package com.example.saurabh_pc.medcare3;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MapStartActivity extends AppCompatActivity {

    private static final String TAG = "MapStartActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_start);
        Thread mapThread= new Thread(){
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(1500);
                }catch (Exception e)
                {
                }finally {
                    if(isServicesOK())
                    {
                        init();
                    }
                }
            }
        };
        mapThread.start();

    }

    private void init() {
        Intent intent= new Intent(MapStartActivity.this, MapActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK : checking google services version");
        int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapStartActivity.this);
        if (available== ConnectionResult.SUCCESS)
        {
            Log.d(TAG, "isServicesOK: Google play services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            Log.d(TAG, "isServicesOK: an error occured but we can fix it shortly");
            Dialog dialog= GoogleApiAvailability.getInstance().getErrorDialog(MapStartActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else
        {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
