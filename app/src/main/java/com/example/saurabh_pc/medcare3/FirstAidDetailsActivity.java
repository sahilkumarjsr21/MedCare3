package com.example.saurabh_pc.medcare3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;

public class FirstAidDetailsActivity extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid_details);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            String name = extra.getString("Name");
            Database database = new Database(FirstAidDetailsActivity.this);
            String fileName = database.getFileName(name);
            fileName = fileName.trim();
            fileName=fileName+".pdf";
            pdfView = (PDFView) findViewById(R.id.pdf);
            pdfView.fromAsset(fileName)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .enableAntialiasing(true)
                    .autoSpacing(true)
                    .pageFitPolicy(FitPolicy.WIDTH)
                    .pageSnap(true)
                    .scrollHandle(new DefaultScrollHandle(this))                //.pageFling(true)
                    .load();
            pdfView.useBestQuality(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
