package com.example.saurabh_pc.medcare3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.StandardSocketOptions;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.saurabh_pc.medcare3.Constants.ip_address;
import static com.example.saurabh_pc.medcare3.R.id.doctor_image;

public class DoctorProfileActivity extends AppCompatActivity implements DistCalcTask.Geo
{

    CircleImageView doctor_image;
    TextView doctor_name, doctor_specialist_in, doctor_degree, doctor_experience, doctor_consultation_fee,
            doctor_timing, doctor_days, doctor_rating, doctor_location;
    Button doctor_btn, review_btn;

    LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
         lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        doctor_image = (CircleImageView) findViewById(R.id.doctor_image);
        doctor_name = (TextView) findViewById(R.id.doctor_name);
        doctor_specialist_in = (TextView) findViewById(R.id.doctor_specialist_in);
        doctor_degree = (TextView) findViewById(R.id.doctor_degree);
        doctor_experience = (TextView) findViewById(R.id.doctor_experience);
        doctor_rating = (TextView) findViewById(R.id.doctor_rating);
        doctor_consultation_fee = (TextView) findViewById(R.id.doctor_consultation_fee);
        doctor_timing = (TextView) findViewById(R.id.doctor_timing);
        doctor_days = (TextView) findViewById(R.id.doctor_days);
        doctor_location = (TextView) findViewById(R.id.doctor_location);
        doctor_btn = (Button) findViewById(R.id.doctor_btn);
        review_btn= (Button)findViewById(R.id.review_btn);

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String id = extras.getString("id");
            doctor_name.setText(extras.getString("name"));
            doctor_degree.setText(extras.getString("degree"));
            doctor_specialist_in.setText(extras.getString("specialist_in"));
            doctor_experience.setText(extras.getString("experience"));
            doctor_rating.setText(extras.getString("rating"));
            doctor_consultation_fee.setText("Rs. " + extras.getString("consultation_fee"));
            doctor_timing.setText(extras.getString("timing"));
            doctor_days.setText(extras.getString("days"));

            review_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i= new Intent(DoctorProfileActivity.this, ReviewPageActivity.class);
                    i.putExtra("id", id);
                    startActivity(i);

                }
            });

            doctor_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + extras.getString("ph_no")));//change the number
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                        if (ContextCompat.checkSelfPermission(DoctorProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(DoctorProfileActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                        } else {
                            startActivity(callIntent);
                        }
                    }
                }
            });

            final String IMG_URL = "http://" + ip_address + "/MedCare/doctor_image_db/image" + extras.getString("id") + ".jpg";
            Glide.with(DoctorProfileActivity.this).load(IMG_URL).thumbnail(0.5f).crossFade()
                    .into(doctor_image);
            //Toast.makeText(this, extras.getString("rating"), Toast.LENGTH_LONG).show();

            getDeviceLocation();


        }
    }


    @Override
    public void setDouble(String result) {
        String res[]=result.split(",");
        Double min=Double.parseDouble(res[0])/60;
        int dist=Integer.parseInt(res[1])/1000;
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            StringTokenizer str= new StringTokenizer(extras.getString("location"),";");
            String locationName= str.nextToken();
            doctor_location.setText(locationName+"\nDistance= " + dist + " km\nDuration via road= " + (int) (min / 60) + " hr " + (int) (min % 60) + " mins");
        }

    }


    //-------------------------------------------------------------------------------------------------------

    double lat=0;
    double lng=0;
    private FusedLocationProviderClient mFusedLocationProvideClient;
    private boolean mLocationPermissionGranted = false;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private void getDeviceLocation() {
        getLocationPermission();
        mFusedLocationProvideClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                final Task location = mFusedLocationProvideClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            lat=currentLocation.getLatitude();
                            lng=currentLocation.getLongitude();
                            System.out.println("lat="+lat+" lng="+lng);
                            if(lat==0.0 && lng==0.0)
                            {
                                //Toast.makeText(this, "lat="+lat+" lng="+lng, Toast.LENGTH_SHORT).show();
                                doctor_location.setText("cannot get details");

                            }
                            else {

                                String url1 = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + lat + "," + lng + "&destinations=";

                                final Bundle extras = getIntent().getExtras();
                                if (extras != null) {
                                    StringTokenizer st = new StringTokenizer(extras.getString("location"), ";");

                                    String locationName = "";
                                    String Lat = "";
                                    String Lng = "";

                                    try {
                                        locationName = st.nextToken();
                                        Lat = st.nextToken().trim();
                                        Lng = st.nextToken().trim();
                                    } catch (NoSuchElementException e) {
                                        locationName = "cannot fetch details";
                                    }

                                    if ((locationName == "" || Lat == "" || Lng == "")) {
                                        doctor_location.setText("cannot get details");
                                    } else {
                                        url1 += Lat + "," + Lng + "&key=AIzaSyCv5LLbW7nG_NnPjn0953t_Dyzt0_o6bIM";
                                        //Toast.makeText(this, url1, Toast.LENGTH_LONG).show();
                                        System.out.println(url1);
                                        new DistCalcTask(DoctorProfileActivity.this).execute(url1);
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(DoctorProfileActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("DoctorProfileActivity", "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void getLocationPermission(){
        String[] permissions= {android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        //android.Manifest.permission.ACCESS_FINE_LOCATION

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                mLocationPermissionGranted = true;
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
                }
            }
        }

    }

}
