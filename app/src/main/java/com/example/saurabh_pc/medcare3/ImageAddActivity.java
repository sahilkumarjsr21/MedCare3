package com.example.saurabh_pc.medcare3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ImageAddActivity extends AppCompatActivity {

    final int REQUEST_CODE_GALLERY=999;

    imageDbHelper imageDbHelper;
    ImageView myImg;
    Button chooseImage, addImage, resetBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_add);

        imageDbHelper= new imageDbHelper(this);

        myImg= (ImageView)findViewById(R.id.imageView);
        chooseImage=(Button)findViewById(R.id.chooseImage);
        addImage=(Button)findViewById(R.id.addImage);
        resetBtn=(Button)findViewById(R.id.resetBtn);

        addImage.setVisibility(View.GONE);
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ImageAddActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
                addImage.setVisibility(View.VISIBLE);

            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =(SharedPrefManager.getInstance(ImageAddActivity.this).getUsername());
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
                String Date = sdf.format(c.getTime());

                byte[] newImage = imageViewTobyte(myImg);

                AddData(name, Date, newImage);

            }

            private byte[] imageViewTobyte(ImageView myImg) {
                Bitmap bitmap = ((BitmapDrawable)myImg.getDrawable()).getBitmap();
                ByteArrayOutputStream stream= new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] byteArray = stream.toByteArray();
                return byteArray;
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDbHelper.ResetTable();
            }
        });

    }

    private void AddData(String name, String date, byte[] newImage) {
        boolean isInsertData = imageDbHelper.insertImage(name, date, newImage);
        if(isInsertData)
            Toast.makeText(this, "Image inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Image not inserted", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }else{
                Toast.makeText(this, "you dont have the permission to access file", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try{
            Uri uri = data.getData();
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            myImg.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            System.out.println("Null passed in URI");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
