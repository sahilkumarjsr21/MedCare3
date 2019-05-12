package com.example.saurabh_pc.medcare3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

public class DoctorCategoryActivity extends AppCompatActivity {

    TextView Search_Ophthalmologist, Search_Dermatologist, Search_Cardiologist, Search_Gastroenterologist,
            Search_Phychiatrist, Search_ENT_Specialist, Search_Gynecologist, Search_Neurologist,
            Search_Dentist, Search_Urologist, Search_Physiotherapist, Search_Phychologist,
            Search_Audiologist, Search_Nutritionist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_category);

        Search_Ophthalmologist = (TextView)findViewById(R.id.Search_Ophthalmologist);
        Search_Dermatologist = (TextView)findViewById(R.id.Search_Dermatologist);
        Search_Cardiologist = (TextView)findViewById(R.id.Search_Cardiologist);
        Search_Gastroenterologist = (TextView)findViewById(R.id.Search_Gastroenterologist);
        Search_Phychiatrist = (TextView)findViewById(R.id.Search_Phychiatrist);
        Search_Gynecologist = (TextView)findViewById(R.id.Search_Gynecologist);
        Search_Neurologist = (TextView)findViewById(R.id.Search_Neurologist);
        Search_Dentist = (TextView)findViewById(R.id.Search_Dentist);
        Search_Urologist = (TextView)findViewById(R.id.Search_Urologist);
        Search_Physiotherapist = (TextView)findViewById(R.id.Search_Physiotherapist);
        Search_Phychologist = (TextView)findViewById(R.id.Search_Phychologist);
        Search_Audiologist = (TextView)findViewById(R.id.Search_Audiologist);
        Search_Nutritionist = (TextView)findViewById(R.id.Search_Nutritionist);


        Search_Ophthalmologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DoctorCategoryActivity.this, DoctorsListActivity.class);
                i.putExtra("message", Search_Ophthalmologist.getText().toString());
                startActivity(i);
            }
        });


        Search_Dermatologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DoctorCategoryActivity.this, DoctorsListActivity.class);
                i.putExtra("message",Search_Dermatologist.getText().toString());
                startActivity(i);
            }
        });

        Search_Cardiologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DoctorCategoryActivity.this, DoctorsListActivity.class);
                i.putExtra("message",Search_Cardiologist.getText().toString());
                startActivity(i);
            }
        });

        Search_Gastroenterologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DoctorCategoryActivity.this, DoctorsListActivity.class);
                i.putExtra("message",Search_Gastroenterologist.getText().toString());
                startActivity(i);
            }
        });

        Search_Phychiatrist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DoctorCategoryActivity.this, DoctorsListActivity.class);
                i.putExtra("message",Search_Phychiatrist.getText().toString());
                startActivity(i);
            }
        });

        Search_Gynecologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DoctorCategoryActivity.this, DoctorsListActivity.class);
                i.putExtra("message",Search_Gynecologist.getText().toString());
                startActivity(i);
            }
        });

        Search_Neurologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DoctorCategoryActivity.this, DoctorsListActivity.class);
                i.putExtra("message",Search_Neurologist.getText().toString());
                startActivity(i);
            }
        });

        Search_Dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DoctorCategoryActivity.this, DoctorsListActivity.class);
                i.putExtra("message",Search_Dentist.getText().toString());
                startActivity(i);
            }
        });

        Search_Urologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DoctorCategoryActivity.this, DoctorsListActivity.class);
                i.putExtra("message",Search_Urologist.getText().toString());
                startActivity(i);
            }
        });

        Search_Physiotherapist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DoctorCategoryActivity.this, DoctorsListActivity.class);
                i.putExtra("message",Search_Physiotherapist.getText().toString());
                startActivity(i);
            }
        });

        Search_Phychologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DoctorCategoryActivity.this, DoctorsListActivity.class);
                i.putExtra("message",Search_Phychologist.getText().toString());
                startActivity(i);
            }
        });

        Search_Audiologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DoctorCategoryActivity.this, DoctorsListActivity.class);
                i.putExtra("message",Search_Audiologist.getText().toString());
                startActivity(i);
            }
        });

        Search_Nutritionist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DoctorCategoryActivity.this, DoctorsListActivity.class);
                i.putExtra("message",Search_Nutritionist.getText().toString());
                startActivity(i);
            }
        });
    }
}
