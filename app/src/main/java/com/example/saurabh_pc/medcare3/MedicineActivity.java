package com.example.saurabh_pc.medcare3;

import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.saurabh_pc.medcare3.Adapter.NameAdapter;
import com.example.saurabh_pc.medcare3.MedDatabase.Database;
import com.example.saurabh_pc.medcare3.Model.Medicine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;

public class MedicineActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ArrayList<Medicine> list= new ArrayList<>();
    NameAdapter adapter;
    public IndexFastScrollRecyclerView recyclerView;
    public List<AlphabetItem> mAlphabetItems;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Drugs Library");
        Database database= new Database(MedicineActivity.this);
        File file=getApplicationContext().getDatabasePath(Database.DATABASE_NAME);
        if(file.exists()==false)
        {
            database.getReadableDatabase();
            if(copyDatabase(MedicineActivity.this))
            {
                Log.d("DATBASE COPIED","Successful");
            }
            else
            {
                Toast.makeText(this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        recyclerView= (IndexFastScrollRecyclerView) findViewById(R.id.recyclerView);
        list.addAll(database.getNames());
        mAlphabetItems= new ArrayList<>();
        List<String> strAlphabets = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list == null || list.get(i).getName().trim().isEmpty()) {
                continue;
            }
            String word = list.get(i).getName().substring(0, 1);
            if (!strAlphabets.contains(word)) {
                strAlphabets.add(word);
                mAlphabetItems.add(new AlphabetItem(i, word, false));
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter= new NameAdapter(MedicineActivity.this,list);
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


//        Iterator i= list.iterator();
//        while(i.hasNext()) {
//
//            Medicine medicine= new Medicine();
//        medicine= (Medicine) i.next();
//        Log.d("Names",medicine.getName());
//        }
//        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.list_items);
    }
    public boolean copyDatabase(Context context)
    {
        try {
            InputStream inputStream= context.getAssets().open(Database.DATABASE_NAME);
            String outFileName=Database.PATH+Database.DATABASE_NAME;
            OutputStream stream=new FileOutputStream(outFileName);
            byte buff[]= new byte[1024];
            int length;
            while((length=inputStream.read(buff))>0)
            {
                stream.write(buff,0,length);
            }
            stream.flush();
            stream.close();
            Log.d("TAG", "copyDatabase: database copied");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
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
        newText=newText.toLowerCase();
        String str;
        ArrayList<Medicine> arrayList= new ArrayList<Medicine>();
        for(Medicine i:list)
        {
            String name=i.getName().toLowerCase();
            if(name.contains(newText))
            {
                arrayList.add(i);
            }
        }
        adapter.setFilter(arrayList);
        return true;
    }
}
