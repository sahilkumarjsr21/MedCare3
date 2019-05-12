package com.example.saurabh_pc.medcare3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView btn_maps, btn_pharmacy,btn_chat,btn_appointment, btn_emr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_maps= (ImageView) findViewById(R.id.btn_maps);
        btn_pharmacy= (ImageView)findViewById(R.id.btn_medicine);
        btn_chat=(ImageView)findViewById(R.id.chatbot_icon_wide);
        btn_appointment= (ImageView)findViewById(R.id.btn_appointment);
        btn_emr = (ImageView)findViewById(R.id.emrIcon);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btn_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,MapStartActivity.class));
            }
        });

        btn_pharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MedicinesActivity.class));
            }
        });
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ChatActivity.class));
            }
        });
        btn_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, DoctorCategoryActivity.class));
            }
        });
        btn_emr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, EMRActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            SharedPrefManager.getInstance(HomeActivity.this).logout();
            Intent i= new Intent(HomeActivity.this, LoginActivity.class);
            finish();
            return true;
        }

        else if (id == R.id.action_emergencyCall) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "102", null));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_prescription) {
            startActivity(new Intent(HomeActivity.this, PrescriptionFormActivity.class));
        } else if (id == R.id.nav_reminder) {
            startActivity(new Intent(HomeActivity.this,MainActivity.class));
        } else if (id == R.id.nav_library) {
            startActivity(new Intent(HomeActivity.this,MedicineActivity.class));
        } else if (id == R.id.nav_account) {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        }
        else if (id == R.id.nav_first_aid) {
            startActivity(new Intent(HomeActivity.this, FirstAidActivity.class));
        }
        else if (id == R.id.nav_helpcenter) {

        } else if (id == R.id.nav_aboutUs) {
            startActivity(new Intent(HomeActivity.this, AboutUsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
