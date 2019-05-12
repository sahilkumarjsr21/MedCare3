package com.example.saurabh_pc.medcare3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    TextView profile_name, profile_email, profile_username, profile_gender, profile_age, profile_id;
    Button btn_update;
    public String id = SharedPrefManager.getInstance(ProfileActivity.this).getUserID().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile_name = (TextView)findViewById(R.id.profile_name);
        profile_email= (TextView)findViewById(R.id.profile_email);
        profile_username= (TextView)findViewById(R.id.profile_username);
        profile_gender= (TextView)findViewById(R.id.profile_gender);
        profile_age= (TextView)findViewById(R.id.profile_age);
        profile_id= (TextView)findViewById(R.id.profile_id);
        btn_update = (Button)findViewById(R.id.btn_update);

        profile_email.setText(SharedPrefManager.getInstance(this).getUserEmail());
        profile_username.setText(SharedPrefManager.getInstance(this).getUsername());
        profile_id.setText(SharedPrefManager.getInstance(this).getUserID().toString());
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_USER_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj= new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                String name= obj.getString("name");
                                String gender= obj.getString("gender");
                                String age= Integer.toString(obj.getInt("age"));
                                profile_name.setText(name);
                                profile_gender.setText(gender);
                                profile_age.setText(age);
                            }else{
                                Toast.makeText(ProfileActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("id", SharedPrefManager.getInstance(ProfileActivity.this).getUserID().toString());
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, UpdateInfoActivity.class));
                finish();
            }
        });
    }
}
