package com.example.saurabh_pc.medcare3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saurabh_pc.medcare3.models.PlaceInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;
    //private ImageView uber_icon;

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private static final String TAG = "Map2Activity";
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final float DEFAULT_ZOOM = 15f;
    private boolean mLocationPermissionGranted = false;
    private static final LatLngBounds LAT_LNG_BOUNDS= new LatLngBounds
            (new LatLng(-40, -168), new LatLng(71, 136));

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProvideClient;

    private PlaceInfo mPlace;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Marker mMarker;
    private static final int PLACE_PICKER_REQUEST = 1;

    private AutoCompleteTextView mSearchText;
    private ImageView mGPS, mInfo, mPlacePicker, mHospital, mPharmacy;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mSearchText= (AutoCompleteTextView) findViewById(R.id.input_search);
        mInfo= (ImageView)findViewById(R.id.place_info);
        mGPS= (ImageView)findViewById(R.id.ic_gps);
        mPlacePicker= (ImageView)findViewById(R.id.ic_map);
        mHospital= (ImageView)findViewById(R.id.hospital_btn);
        mPharmacy= (ImageView)findViewById(R.id.ic_pharmacy);
        //uber_icon= (ImageView)findViewById(R.id.uber_btn);
        getLocationPermission();

    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting current device location");
        mFusedLocationProvideClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                final Task location = mFusedLocationProvideClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location");
                            Location currentLocation = (Location) task.getResult();
                            latitude=currentLocation.getLatitude();
                            longitude=currentLocation.getLongitude();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "My Location");
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }catch (NullPointerException e){
            latitude =0.0;
            longitude =0.0;
            getLocationPermission();
        }
    }

    private void init(){
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mSearchText.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceAutocompleteAdapter= new PlaceAutocompleteAdapter(this,
                Places.getGeoDataClient(getActivity(), null), LAT_LNG_BOUNDS, null);

        mSearchText.setAdapter(mPlaceAutocompleteAdapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId== EditorInfo.IME_ACTION_DONE ||
                        keyEvent.getAction()==keyEvent.ACTION_DOWN ||
                        keyEvent.getAction()== keyEvent.KEYCODE_ENTER){
                    //execute out method for searcvhing or geoLocate
                    geoLocate();
                }
                return false;
            }
        });

        mGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
                //uber_icon.setVisibility(View.INVISIBLE);
            }
        });

        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked place info");
                try{
                    if(mMarker.isInfoWindowShown()){
                        mMarker.hideInfoWindow();
                    }
                    else {
                        Log.d(TAG, "onClick: place info" +mPlace.toString() );
                        mMarker.showInfoWindow();
                    }
                }catch (NullPointerException e){
                    Log.e(TAG, "onClick: NullPointerException"+e.getMessage() );
                }
            }
        });

        mPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int PLACE_PICKER_REQUEST = 1;
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(MapActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesRepairableException" +e.getMessage() );
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesNotAvailableException" +e.getMessage() );
                }
            }
        });

        mHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HospitalsgeoLocate();
            }
        });
        hideSoftKeyboard();

        mPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PharmacygeoLocate();
            }
        });
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);

                PendingResult<PlaceBuffer> placeResult= Places.GeoDataApi.getPlaceById(mGoogleApiClient, place.getId());
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            }
        }
    }

    private void geoLocate(){
        Log.d(TAG, "geoLocate: geoLocating...");
        String searchString= mSearchText.getText().toString();
        Geocoder geocoder= new Geocoder(MapActivity.this);
        List<Address> list= new ArrayList<>();
        try{
            list= geocoder.getFromLocationName(searchString, 1);

        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException" +e.getMessage() );
        }

        if(list.size()>0){
            Address address= list.get(0);
            Log.d(TAG, "geoLocate: found a location: "+ address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,  address.getAddressLine(0));
            //uber_icon.setVisibility(View.VISIBLE);
        }
    }

    private void moveCamera(LatLng latLng, float zoom, PlaceInfo placeInfo) {
        Log.d(TAG, "moveCamera: moving the camera to lat:" + latLng.latitude + ", lng:" + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        mMap.clear();

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapActivity.this));

        if(placeInfo!= null) {
            try{
                String snippet= "Address: "+placeInfo.getAddress()+"\n"+
                        "Phone Number: "+placeInfo.getPhoneNumber()+"\n"+
                        "Website: "+placeInfo.getWebsiteUri()+"\n"+
                        "Rating: "+placeInfo.getRating();

                MarkerOptions options= new MarkerOptions().position(latLng)
                        .title(placeInfo.getName()).snippet(snippet);
                mMarker= mMap.addMarker(options);
                //uber_icon.setVisibility(View.VISIBLE);

            }catch (NullPointerException e){
                Log.e(TAG, "moveCamera: NullPointerException "+ e.getMessage() );
            }
        }else {
            mMap.addMarker(new MarkerOptions().position(latLng));
        }

        hideSoftKeyboard();
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to lat:" + latLng.latitude + ", lng:" + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location"))
        {
            MarkerOptions options= new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(options);
        }
        hideSoftKeyboard();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Toast.makeText(this, "Map is READY", Toast.LENGTH_SHORT).show();
        boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources()
                .getString(R.string.style_json)));

        if (!success) {
            Log.e(TAG, "Style parsing failed.");
        }
        mMap= googleMap;

        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.getUiSettings().setMapToolbarEnabled(false);
            mMap.getUiSettings().setRotateGesturesEnabled(true);
            init();
        }
    }

    private void initMap(){
        SupportMapFragment mapFragment= (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission(){
        String[] permissions= {android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        //android.Manifest.permission.ACCESS_FINE_LOCATION

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                mLocationPermissionGranted = true;
                initMap();
            }
            else
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
        else
            ActivityCompat.requestPermissions(this, permissions,LOCATION_PERMISSION_REQUEST_CODE );

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted= false;

        switch (requestCode)
        {
            case LOCATION_PERMISSION_REQUEST_CODE:
            {
                if(grantResults.length>0){
                    for(int i=0; i< grantResults.length; i++)
                    {
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED)
                        {
                            mLocationPermissionGranted=false;
                            return;
                        }
                    }
                    mLocationPermissionGranted=true;
                    //initialize our map
                    initMap();
                }
            }
        }

    }
    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    public Context getActivity() {
        return MapActivity.this;
    }

    /*---------------------------------google places api autocomplete suggessions-----------------------------------*/
    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hideSoftKeyboard();

            final AutocompletePrediction item= mPlaceAutocompleteAdapter.getItem(i);
            final String placeId= item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult= Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback= new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            mMap.clear();
            if(!places.getStatus().isSuccess()) {
                Log.d(TAG, "onResult: Place query didnot complete successfully" +places.getStatus().toString());
                places.release();
                return;
            }
            final Place place= places.get(0);

            try{
                mPlace = new PlaceInfo();
                mPlace.setName(place.getName().toString());
                mPlace.setAddress(place.getAddress().toString());
                mPlace.setId(place.getId());
                mPlace.setLatLng(place.getLatLng());
                mPlace.setRating(place.getRating());
                mPlace.setPhoneNumber(place.getPhoneNumber().toString());
                mPlace.setWebsiteUri(place.getWebsiteUri());
                mPlace.setAttributions(place.getAttributions().toString());
                Log.d(TAG, "onResult: place: "+mPlace.toString());
            }catch (NullPointerException e){
                Log.e(TAG, "onResult: NullPointException " +e.getMessage() );
            }
            //moveCamera(mPlace.getLatLng(), DEFAULT_ZOOM, mPlace.getName());
            moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                    place.getViewport().getCenter().longitude), DEFAULT_ZOOM, mPlace);
            places.release();

        }
    };

    private void HospitalsgeoLocate() {
        mMap.clear();
        String hospital = "hospital";
        String url= getUrl(latitude, longitude, hospital);
        Object []dataTransfer= new Object[2];   //Object[0]=mMap and Object[1]=url
        dataTransfer[0]= mMap;
        dataTransfer[1]= url;

        GetNearbyPlacesData getNearbyPlacesData= new GetNearbyPlacesData();
        getNearbyPlacesData.execute(dataTransfer);
        Toast.makeText(this, "Showing nearby Hospitals", Toast.LENGTH_SHORT).show();
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace){
        StringBuilder googlePlaceUrl= new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);    //this for hospitals
        googlePlaceUrl.append("&name=clinic");  //this for clinics
        googlePlaceUrl.append("&key="+"AIzaSyBdZGAwvqWNhce11UuXP8FyzEpTLHHby3E");
        return googlePlaceUrl.toString();
    }

    private void PharmacygeoLocate(){
        mMap.clear();
        String pharmacy = "pharmacy";
        String url= getUrlforPharmacy(latitude, longitude, pharmacy);
        Object []dataTransfer= new Object[2];   //Object[0]=mMap and Object[1]=url
        dataTransfer[0]= mMap;
        dataTransfer[1]= url;

        GetNearbyPlacesData getNearbyPlacesData= new GetNearbyPlacesData();
        getNearbyPlacesData.execute(dataTransfer);
        Toast.makeText(this, "Showing nearby pharmacy", Toast.LENGTH_SHORT).show();
    }

    private String getUrlforPharmacy(double latitude, double longitude, String nearbyPlace){
        StringBuilder googlePlaceUrl= new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);    //this for hospitals
        googlePlaceUrl.append("&key="+"AIzaSyBdZGAwvqWNhce11UuXP8FyzEpTLHHby3E");
        return googlePlaceUrl.toString();
    }
}
