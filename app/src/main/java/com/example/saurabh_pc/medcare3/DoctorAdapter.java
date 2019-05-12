package com.example.saurabh_pc.medcare3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.example.saurabh_pc.medcare3.Constants.ip_address;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>{

    private Context mCtx;
    Button btn;
    private List<detailsDoctor> doctorList;

    public DoctorAdapter(Context mCtx, List<detailsDoctor> doctorList) {
//        this.mCtx = mCtx;
        this.doctorList = doctorList;
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
        DoctorViewHolder holder = new DoctorViewHolder(view);
        btn=(Button) holder.bookDoctorBtn.findViewById(R.id.bookDoctorBtn);
        ImageView imgview=(ImageView)holder.imageDoctor.findViewById(R.id.imageDoctor);
        mCtx=view.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DoctorViewHolder holder, int position) {
        final detailsDoctor details = doctorList.get(position);

        holder.nameDoctor.setText("Dr. "+details.getName());
        holder.speclistDoctor.setText(details.getSpecialist_in());
        holder.doctorDetails.setText("Consultation fee: Rs."+details.getConsultation_fee()+"\nexperience: "+details.getExperience());
        holder.ratingDoctor.setText(details.getRating());
        holder.bookDoctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mCtx, details.getPh_no(), Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+details.getPh_no()));//change the number
                if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.DONUT) {
                    if(ContextCompat.checkSelfPermission(mCtx, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
                        DoctorsListActivity x=(DoctorsListActivity) mCtx;
                        ActivityCompat.requestPermissions(x, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }else
                    {
                        mCtx.startActivity(callIntent);
                    }
                }
            }
        });
        final String IMG_URL= "http://"+ip_address+"/MedCare/doctor_image_db/image"+details.getId()+".jpg";
        Log.d("URL",IMG_URL);
        ImageView imageView = (ImageView)holder.imageDoctor.findViewById(R.id.imageDoctor);

        DoctorsListActivity x=(DoctorsListActivity) mCtx;
        Glide.with(x).load(IMG_URL).thumbnail(0.5f).crossFade()
                .into(imageView);

        holder.Doc_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(mCtx, DoctorProfileActivity.class);
                int id1= details.getId();
                String id2= String.valueOf(id1);
                i.putExtra("id", id2);
                i.putExtra("name", details.getName());
                i.putExtra("degree", details.getDegree());
                i.putExtra("specialist_in", details.getSpecialist_in());
                i.putExtra("experience", details.getExperience());
                i.putExtra("rating", details.getRating());
                i.putExtra("consultation_fee", details.getConsultation_fee());
                i.putExtra("location", details.getLocation());
                i.putExtra("timing", details.getTiming());
                i.putExtra("days", details.getDays());
                i.putExtra("ph_no", details.getPh_no());
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public void setFilter(ArrayList<detailsDoctor> filter) {
        doctorList= new ArrayList<>();
        doctorList.addAll(filter);
        notifyDataSetChanged();
    }


    class DoctorViewHolder extends RecyclerView.ViewHolder{

        ImageView imageDoctor;
        TextView nameDoctor, speclistDoctor, ratingDoctor, doctorDetails;
        Button bookDoctorBtn;
        RelativeLayout Doc_tab;

        public DoctorViewHolder(View itemView) {
            super(itemView);

            imageDoctor = itemView.findViewById(R.id.imageDoctor);
            nameDoctor = itemView.findViewById(R.id.nameDoctor);
            speclistDoctor = itemView.findViewById(R.id.speclistDoctor);
            ratingDoctor = itemView.findViewById(R.id.ratingDoctor);
            doctorDetails = itemView.findViewById(R.id.doctorDetails);
            bookDoctorBtn= itemView.findViewById(R.id.bookDoctorBtn);
            Doc_tab= itemView.findViewById(R.id.Doc_tab);

        }
    }

}

