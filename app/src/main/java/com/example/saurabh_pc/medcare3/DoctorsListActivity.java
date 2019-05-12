package com.example.saurabh_pc.medcare3;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorsListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView searchView;

    RecyclerView recyclerView;
    DoctorAdapter adapter;
    List<detailsDoctor> doctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);

        doctorList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView=(SearchView)findViewById(R.id.search);
        loadDoctorsList();

    }

    private void loadDoctorsList(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_FILTER_DOCTOR_LiST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("response"+response);
                            JSONArray detailsList = new JSONArray(response);

                            for(int i=0; i<detailsList.length(); i++) {
                                JSONObject detailsDoctor = detailsList.getJSONObject(i);

                                int id= detailsDoctor.getInt("id");
                                String name = detailsDoctor.getString("name");
                                String degree = detailsDoctor.getString("degree");
                                String specialist_in = detailsDoctor.getString("specialist_in");
                                String experience = detailsDoctor.getString("experience");
                                String rating = detailsDoctor.getString("rating");
                                String consultation_fee = detailsDoctor.getString("consultation_fee");
                                String location = detailsDoctor.getString("location");
                                String timing = detailsDoctor.getString("timing");
                                String days = detailsDoctor.getString("days");
                                String ph_no = detailsDoctor.getString("ph_no");

                                detailsDoctor detailsDoctor1= new detailsDoctor(id, name, degree, specialist_in, experience,
                                        rating, consultation_fee, location, timing, days, ph_no);

                                doctorList.add(detailsDoctor1);

                                adapter = new DoctorAdapter(DoctorsListActivity.this, doctorList);
                                recyclerView.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DoctorsListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                String s="";
                Bundle b= getIntent().getExtras();
                if(b!=null){
                    s= b.getString("message");
                    params.put("specialist_in", s);
                }
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchfile, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView s =(SearchView) MenuItemCompat.getActionView(search);
        s.setOnQueryTextListener(this);

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
        ArrayList<detailsDoctor> newList= new ArrayList<>();
        for(detailsDoctor i: doctorList)
        {
            String name =i.getName().toLowerCase();
            if(name.contains(newText))
            {
                newList.add(i);
            }
        }
        adapter.setFilter(newList);
        return false;
    }



}
