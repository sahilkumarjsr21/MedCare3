package com.example.saurabh_pc.medcare3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionListViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PrescriptionAdapter adapter;
    List<pres_details> presList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_list_view);

        presList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.pres_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadPresList();

//        PrescriptionDbHelper user = new PrescriptionDbHelper(this);
//        SQLiteDatabase db = user.getReadableDatabase();
//
//        Cursor cursor = user.getAllData();
//        System.out.println("CURSOR COUNT= "+cursor.getCount());
//        if(!(cursor.getCount() ==0)){
//            PrescriptionCursorAdapter prescriptionCursorAdapter = new PrescriptionCursorAdapter(this, cursor, 0);
//            ListView pres_list = findViewById(R.id.pres_list);
//            pres_list.setAdapter(prescriptionCursorAdapter);
//        }
//        else{
//            Toast.makeText(this, "No prescriptions", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(PrescriptionListViewActivity.this, HomeActivity.class));
//            finish();
//        }

    }

    private void loadPresList() {
        PrescriptionDbHelper user = new PrescriptionDbHelper(this);
        Cursor cursor = user.searchdata(SharedPrefManager.getInstance(this).getUsername().trim());

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {


            if (cursor != null) {
                String id = cursor.getString(0);
                String user_id = cursor.getString(1);
                String date = cursor.getString(2);
                String diagnosed_with = cursor.getString(3);
                String blood_pressure = cursor.getString(4);
                String things_to_follow = cursor.getString(5);
                String physician_name = cursor.getString(6);
                String registration_number = cursor.getString(7);
                String drug_and_dosage = cursor.getString(8);

                pres_details details = new pres_details(Integer.parseInt(id), user_id, date, diagnosed_with, blood_pressure,
                        things_to_follow, physician_name, registration_number, drug_and_dosage);

                presList.add(details);
            }
            cursor.moveToNext();
        }
        adapter = new PrescriptionAdapter(PrescriptionListViewActivity.this, presList);
        recyclerView.setAdapter(adapter);
    }
}
