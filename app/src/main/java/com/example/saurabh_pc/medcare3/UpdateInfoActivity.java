package com.example.saurabh_pc.medcare3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateInfoActivity extends AppCompatActivity {

    EditText update_name,update_age;
    RadioGroup gender_rg;
    RadioButton rb;
    Button update_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        update_age=(EditText)findViewById(R.id.update_age);
        update_name=(EditText)findViewById(R.id.update_name);
        gender_rg=(RadioGroup) findViewById(R.id.gender_rg);
        update_btn=(Button)findViewById(R.id.btn_update);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInformation();
            }
        });
    }

    public void updateInformation() {
        int selected_id = gender_rg.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(selected_id);
        final String name = update_name.getText().toString().trim();
        final String age = update_age.getText().toString().trim();
        final String gender;
        String gender1;
        try {
            gender1 = rb.getText().toString();
        }catch (NullPointerException e){
            gender1 ="";
        }

        gender = gender1;
        final String id = SharedPrefManager.getInstance(this).getUserID().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_USER_UPDATE_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(UpdateInfoActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            if(obj.getString("message")=="Information registered successfully")
                                startActivity(new Intent(UpdateInfoActivity.this, HomeActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateInfoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("id", id);
                params.put("name", name);
                params.put("gender", gender);
                params.put("age", age);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
