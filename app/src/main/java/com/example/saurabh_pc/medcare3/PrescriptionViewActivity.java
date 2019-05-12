package com.example.saurabh_pc.medcare3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.StringTokenizer;

public class PrescriptionViewActivity extends AppCompatActivity {

    TextView date, doctorName, registrationNumber, patientName, diagnosedWith, bloodPressure, thingsTocare, drugs_dosage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_view);

        date = (TextView)findViewById(R.id.date);
        doctorName = (TextView)findViewById(R.id.doctorName);
        registrationNumber=(TextView)findViewById(R.id.regNumber);
        patientName = (TextView)findViewById(R.id.patientName);
        diagnosedWith = (TextView)findViewById(R.id.diagnosedWith);
        bloodPressure = (TextView)findViewById(R.id.bloodPressure);
        thingsTocare = (TextView)findViewById(R.id.warning);
        drugs_dosage = (TextView)findViewById(R.id.medDrugs);

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            date.setText(extras.getString("date"));
            doctorName.setText(extras.getString("physician_name"));
            registrationNumber.setText(extras.getString("registration_number"));
            patientName.setText(extras.getString("user_id"));
            diagnosedWith.setText(extras.getString("diagnosed_with"));
            bloodPressure.setText(extras.getString("blood_pressure"));
            thingsTocare.setText(extras.getString("things_to_follow"));

            StringTokenizer st = new StringTokenizer(extras.getString("drug_and_dosage"),";");
            String drugs="";
            while(st.hasMoreTokens()){
                drugs+=st.nextToken()+"\n";
            }
            drugs_dosage.setText(drugs);
        }
    }
}
