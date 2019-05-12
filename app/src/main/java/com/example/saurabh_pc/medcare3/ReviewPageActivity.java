package com.example.saurabh_pc.medcare3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.StringTokenizer;

public class ReviewPageActivity extends AppCompatActivity {

    Button review_submit;
    EditText responseText;

    RecyclerView recyclerView;
    ReviewAdapter adapter;
    List<String> reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);

        review_submit= (Button)findViewById(R.id.review_submit_btn);
        responseText=(EditText)findViewById(R.id.review_submit) ;

        reviewList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showReviews();

        review_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(responseText.getText().toString().trim()!="")
                uploadReviews();
                else
                    Toast.makeText(ReviewPageActivity.this, "Nothing written", Toast.LENGTH_SHORT).show();

                showReviews();
            }
        });
    }

    private void uploadReviews() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REVIEW_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReviewPageActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                String s="";
                Bundle b= getIntent().getExtras();
                if(b!=null){
                    s= b.getString("id");
                    params.put("id", s);
                    params.put("review",responseText.getText().toString().trim());
                }
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void showReviews() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SHOW_REVIEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String review = jsonObject.getString("review");
                            StringTokenizer str= new StringTokenizer(review, ";");
                            while(str.hasMoreTokens()){
                                reviewList.add(str.nextToken());
                            }

                            adapter = new ReviewAdapter(ReviewPageActivity.this, reviewList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReviewPageActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                String s="";
                Bundle b= getIntent().getExtras();
                if(b!=null){
                    s= b.getString("id");
                    params.put("id", s);
                }
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }
}
