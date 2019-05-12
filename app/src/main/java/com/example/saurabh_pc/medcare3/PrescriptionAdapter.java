package com.example.saurabh_pc.medcare3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.presHolder> {

    private Context mCtx;
    private List<pres_details> pres_list;

    public PrescriptionAdapter(Context mCtx, List<pres_details> pres_list) {
//        this.mCtx = mCtx;
        this.pres_list = pres_list;
    }

    @Override
    public presHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_list_item,parent,false);
        PrescriptionAdapter.presHolder holder = new PrescriptionAdapter.presHolder(view);
        mCtx=view.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(presHolder holder, int position) {
        final pres_details details = pres_list.get(position);
        holder.presDate.setText(details.getDate());
        holder.presDiagnosedWith.setText(details.getDiagnosed_with());
        holder.presDoctor.setText("Dr. "+details.getPhysician_name());

        holder.pres_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mCtx, "Intent Bundle "+details.getId(), Toast.LENGTH_SHORT).show();
                Intent i= new Intent(mCtx, PrescriptionViewActivity.class);
                int id1= details.getId();
                String id2= String.valueOf(id1);
                i.putExtra("id", id2);
                i.putExtra("user_id", details.getUser_id());
                i.putExtra("date", details.getDate());
                i.putExtra("diagnosed_with", details.getDiagnosed_with());
                i.putExtra("blood_pressure", details.getBlood_pressure());
                i.putExtra("things_to_follow", details.getThings_to_follow());
                i.putExtra("physician_name", details.getPhysician_name());
                i.putExtra("registration_number", details.getRegistration_number());
                i.putExtra("drug_and_dosage", details.getDrug_and_dosage());
                mCtx.startActivity(i);
            }
        });
    }

    public int getItemCount() {
        return pres_list.size();
    }


    class presHolder extends RecyclerView.ViewHolder{

        TextView presDate, presDoctor, presDiagnosedWith;
        RelativeLayout pres_tab;

        public presHolder(View itemView) {
            super(itemView);
            presDate= itemView.findViewById(R.id.pres_list_date);
            presDiagnosedWith= itemView.findViewById(R.id.pres_list_diagnosed_with);
            presDoctor= itemView.findViewById(R.id.pres_list_doctor_name);
            pres_tab= itemView.findViewById(R.id.pres_list_item);

        }
    }
}
