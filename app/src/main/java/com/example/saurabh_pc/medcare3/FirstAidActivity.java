package com.example.saurabh_pc.medcare3;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;

public class FirstAidActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    Database database;
    List<FirstAid> name;
    IndexFastScrollRecyclerView recyclerView;
    FirstAidAdapter adapter;
    private List<AlphabetItem> mAlphabetItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid);
        database = new Database(FirstAidActivity.this);

        recyclerView = (IndexFastScrollRecyclerView) findViewById(R.id.recyclerView);
        File file = getApplicationContext().getDatabasePath(database.DatabaseName);
        if (file.exists() == false) {
            database.getReadableDatabase();
            if (copyDatabase(FirstAidActivity.this)) {
                Log.d("DATABASE COPIED", "Successful");
            } else {
                Toast.makeText(this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        name = database.getNames();
        mAlphabetItems = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> strAlphabets = new ArrayList<>();
        for (int i = 0; i < name.size(); i++) {
            if (name == null || name.get(i).getName().trim().isEmpty()) {
                continue;
            }
            String word = name.get(i).getName().substring(0, 1);
            if (!strAlphabets.contains(word)) {
                strAlphabets.add(word);
                mAlphabetItems.add(new AlphabetItem(i, word, false));
            }
        }
        adapter = new FirstAidAdapter(FirstAidActivity.this, name);
        recyclerView.setAdapter(adapter);
        recyclerView.setIndexTextSize(10);
        recyclerView.setIndexBarColor("#b2b8c1");
        recyclerView.setIndexBarCornerRadius(10);
        recyclerView.setIndexBarTransparentValue((float) 0.2);
        recyclerView.setIndexbarMargin(4);
        recyclerView.setIndexbarWidth(20);
        recyclerView.setPreviewPadding(2);
        recyclerView.setIndexBarTextColor("#606f84");

//        recyclerView.setPreviewTextSize(60);
//        recyclerView.setPreviewColor("#33334c");
//        recyclerView.setPreviewTextColor("#FFFFFF");
//        recyclerView.setPreviewTransparentValue(0.6f);

        recyclerView.setIndexBarVisibility(true);
        recyclerView.setIndexbarHighLateTextColor("#33334c");
        recyclerView.setIndexBarHighLateTextVisibility(false);
    }



    private boolean copyDatabase(FirstAidActivity mainActivity) {

        try {
            InputStream stream= mainActivity.getAssets().open(Database.DatabaseName);
            String filePath=Database.PATH+Database.DatabaseName;
            OutputStream outputStream= new FileOutputStream(filePath);
            byte buff[]= new byte[1024];
            int length;
            while ((length=stream.read(buff))>0)
            {
                outputStream.write(buff,0,length);
            }
            outputStream.flush();
            outputStream.close();
            Log.d("TAG", "copyDatabase: successful");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuitems,menu);
        MenuItem search=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText= newText.toLowerCase();
        String str;
        List<FirstAid> sa= new ArrayList<>();
        name=new Database(this).getNames();
        for (FirstAid i: name)
        {
            str= i.getName().toLowerCase();
            if(str.contains(newText))
            {
                sa.add(i);
            }
        }
        adapter.setFilter(sa);
        return true;
    }
}
