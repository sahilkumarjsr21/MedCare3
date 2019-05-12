package com.example.saurabh_pc.medcare3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.saurabh_pc.medcare3.MedicineDetailsActivity;
import com.example.saurabh_pc.medcare3.Model.Medicine;
import com.example.saurabh_pc.medcare3.R;

import java.util.ArrayList;
import java.util.List;

public class NameAdapter extends RecyclerView.Adapter<NameAdapterViewHolder> implements SectionIndexer {
    ArrayList<Medicine> name = new ArrayList<>();
    private ArrayList<Integer> mSectionPositions;
    Context context;

    public NameAdapter(Context context, ArrayList name) {
        this.context = context;
        this.name = name;
    }

    @NonNull
    @Override
    public NameAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        NameAdapterViewHolder holder = new NameAdapterViewHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull final NameAdapterViewHolder holder, int position) {
        Log.d("Names BINDVIEW",name.get(position).getName().trim()+"\n");
        holder.textView.setText(name.get(position).getName().trim());

           holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,MedicineDetailsActivity.class);
                intent.putExtra("Data",holder.textView.getText().toString());
                context.startActivity(intent);
                Log.d("Clicked Message",holder.textView.getText().toString()) ;
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }
    public void setFilter(ArrayList<Medicine> nameList)
    {
        name= new ArrayList<>();
        name.addAll(nameList);
        notifyDataSetChanged();
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = name.size(); i < size; i++) {
            String section = String.valueOf(name.get(i).getName().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionIndex);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }
}

class NameAdapterViewHolder extends RecyclerView.ViewHolder{
     CardView cardView;
    TextView textView;
    public NameAdapterViewHolder(View itemView) {
        super(itemView);
        cardView=(CardView)itemView.findViewById(R.id.cardView);
        textView=(TextView)itemView.findViewById(R.id.medText);
    }
}
