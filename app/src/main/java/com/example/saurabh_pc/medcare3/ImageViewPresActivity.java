package com.example.saurabh_pc.medcare3;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ImageViewPresActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    imageAdapter adapter;
    List<imagePres> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_pres);

        imageList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.image_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        try{
        loadImageList();}catch (IllegalStateException e){
            Toast.makeText(this, "cannot display", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadImageList() {
        imageDbHelper user = new imageDbHelper(this);
        Cursor cursor = user.searchdata(SharedPrefManager.getInstance(this).getUsername().trim());

        System.out.println(cursor);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            if (cursor != null) {
                String user_id = cursor.getString(0);
                String date = cursor.getString(1);
                byte[] image = cursor.getBlob(2);

                imagePres details = new imagePres(user_id, date, image);

                imageList.add(details);
            }
            cursor.moveToNext();
        }
        adapter = new imageAdapter(ImageViewPresActivity.this, imageList);
        recyclerView.setAdapter(adapter);
    }
}
