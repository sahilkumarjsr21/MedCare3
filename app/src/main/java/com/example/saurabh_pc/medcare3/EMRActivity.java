package com.example.saurabh_pc.medcare3;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EMRActivity extends AppCompatActivity {

    ImageView viewPresciptions, viewImage;
    Button addImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emr);

        viewPresciptions= (ImageView) findViewById(R.id.viewPresciptions);
        viewImage=(ImageView) findViewById(R.id.viewReports);
        addImage=(Button)findViewById(R.id.add_image);

        viewPresciptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EMRActivity.this, PrescriptionListViewActivity.class));
            }
        });

        viewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EMRActivity.this, ImageViewPresActivity.class));
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EMRActivity.this, ImageAddActivity.class));
            }
        });
    }
}
