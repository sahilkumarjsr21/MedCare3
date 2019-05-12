package com.example.saurabh_pc.medcare3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.TextView;

import com.example.saurabh_pc.medcare3.MedDatabase.Database;
import com.example.saurabh_pc.medcare3.Model.Medicine;

import java.util.ArrayList;

public class MedicineDetailsActivity extends AppCompatActivity {

    CardView cardView;
    TextView name;
    TextView nameDetails;
    TextView about;
    TextView aboutDetails;
    TextView use;
    TextView useDetails;
    TextView how;
    TextView howDetails;
    TextView side;
    TextView sideDetails;
    TextView precaution;
    TextView precautionDetails;
    TextView interaction,interactionDetails,missed,missedDetails,storage,storageDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_details);
        Bundle extra=getIntent().getExtras();
        if (extra!=null)
        {
            String name1=extra.getString("Data");
            Database database= new Database(MedicineDetailsActivity.this);

//            recyclerView=(RecyclerView)findViewById(R.id.detailsRecycler);
            ArrayList<Medicine> list= new ArrayList<>();
            list.addAll(database.getDetails(name1));
//            RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
//            recyclerView.setLayoutManager(layoutManager);
//           DetailsAdapter adapter= new DetailsAdapter(Main2Activity.this,list);
//            recyclerView.setAdapter(adapter);
            cardView=(CardView) findViewById(R.id.card);
            name=(TextView)findViewById(R.id.name);
            nameDetails=(TextView)findViewById(R.id.medicineName);
            about=(TextView) findViewById(R.id.about);
            aboutDetails=(TextView)findViewById(R.id.aboutDetails);
            use=(TextView) findViewById(R.id.use);
            useDetails=(TextView) findViewById(R.id.useDetails);
            how=(TextView) findViewById(R.id.how);
            howDetails=(TextView) findViewById(R.id.howDetails);
            side=(TextView) findViewById(R.id.sideEffect);
            sideDetails=(TextView) findViewById(R.id.sideEffectDetails);
            precaution=(TextView) findViewById(R.id.precaution);
            precautionDetails=(TextView) findViewById(R.id.precautionDetails);
            interaction=(TextView) findViewById(R.id.interaction);
            interactionDetails=(TextView)findViewById(R.id.interactionDetails);
            missed=(TextView) findViewById(R.id.missed);
            missedDetails=(TextView) findViewById(R.id.missedDetails);
            storage=(TextView) findViewById(R.id.storage);
            storageDetails=(TextView) findViewById(R.id.storageDetails);


            int length=list.size();
            Log.d("Length:", String.valueOf(length));

            nameDetails.setText(list.get(0).getName());
            aboutDetails.setText(list.get(0).getAbout());
            useDetails.setText(list.get(0).getUse());
            howDetails.setText(list.get(0).getHow());
            sideDetails.setText(list.get(0).getSide());
            precautionDetails.setText(list.get(0).getPrecaution());
            interactionDetails.setText(list.get(0).getInteraction());
            missedDetails.setText(list.get(0).getMiss());
            storageDetails.setText(list.get(0).getStorage());
        }
    }
}
