package com.example.saurabh_pc.medcare3;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PrescriptionFormActivity extends AppCompatActivity {

    EditText pres_date, pres_diagnosedWith, pres_bloodPressure,
            pres_thingsFollow, pres_physicianName, pres_regNumber, drug_1, drug_2, drug_3, drug_4, drug_5;
    Button current_date_btn, btn_submit, btn_viewAll;
    TextView pres_username;

    PrescriptionDbHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_form);

        myDb = new PrescriptionDbHelper(this);

        pres_username= (TextView) findViewById(R.id.pres_username);
        pres_date = (EditText)findViewById(R.id.pres_date);
        pres_diagnosedWith = (EditText)findViewById(R.id.pres_diagnosedWith);
        pres_bloodPressure = (EditText)findViewById(R.id.pres_bloodPressure);
        pres_thingsFollow = (EditText)findViewById(R.id.pres_thingsFollow);
        pres_physicianName = (EditText)findViewById(R.id.pres_physicianName);
        pres_regNumber = (EditText)findViewById(R.id.pres_regNumber);
        drug_1 = (EditText)findViewById(R.id.drug_1);
        drug_2 = (EditText)findViewById(R.id.drug_2);
        drug_3 = (EditText)findViewById(R.id.drug_3);
        drug_4 = (EditText)findViewById(R.id.drug_4);
        drug_5 = (EditText)findViewById(R.id.drug_5);

        pres_username.setText(SharedPrefManager.getInstance(this).getUsername());

        btn_submit=(Button)findViewById(R.id.btn_submit);
        current_date_btn=(Button)findViewById(R.id.current_date_btn);
        btn_viewAll=(Button)findViewById(R.id.btn_viewall);

        current_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentDateTime();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pres_physicianName.getText().toString()!="" && pres_regNumber.getText().toString()!=null)
                    insertData();
                else
                    Toast.makeText(PrescriptionFormActivity.this, "Doctor's name and registration number must be provided", Toast.LENGTH_SHORT).show();
            }
        });
        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res= myDb.getAllData();
                if(res.getCount() ==0){
                    //Toast.makeText(MainActivity.this, "Error in displaying", Toast.LENGTH_SHORT).show();
                    showMessage("Error", "No data found");
                    return;
                }
                StringBuffer buffer =  new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("ID : "+res.getString(0)+"\n");
                    buffer.append("NAME : "+res.getString(1)+"\n");
                    buffer.append("DATE : "+res.getString(2)+"\n");
                    buffer.append("DIAGNOSED WITH : "+res.getString(3)+"\n");
                    buffer.append("BLOOD PRESSURE : "+res.getString(4)+"\n");
                    buffer.append("THINGS TO FOLLOW : "+res.getString(5)+"\n");
                    buffer.append("PHYSICIAN NAME : "+res.getString(6)+"\n");
                    buffer.append("REG.NO : "+res.getString(7)+"\n");
                    buffer.append("DRUGS & DOSAGE : "+res.getString(8)+"\n\n");
                }

                showMessage("Data", buffer.toString());
            }
        });
    }

    private void insertData() {
        boolean isInserted = myDb.insertPresData(pres_username.getText().toString().trim(),
                pres_date.getText().toString().trim(),
                pres_diagnosedWith.getText().toString().trim(),
                pres_bloodPressure.getText().toString().trim(),
                pres_thingsFollow.getText().toString().trim(),
                pres_physicianName.getText().toString().trim(),
                pres_regNumber.getText().toString().trim(),
                drug_1.getText().toString().trim(),
                drug_2.getText().toString().trim(),
                drug_3.getText().toString().trim(),
                drug_4.getText().toString().trim(),
                drug_5.getText().toString().trim());

        if(isInserted==true){
            Toast.makeText(PrescriptionFormActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(PrescriptionFormActivity.this,  "Data not inserted", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy h:mm a");
        String strDate = mdformat.format(calendar.getTime());
        pres_date.setText(strDate);
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
