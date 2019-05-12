package com.example.saurabh_pc.medcare3;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
        {
            Window w= getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_splash);

        Thread welcomeThread= new Thread(){
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2500);
                }catch (Exception e)
                {
                }finally {
                    Intent home_intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(home_intent);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}
