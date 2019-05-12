package com.example.saurabh_pc.medcare3;

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

import java.util.ArrayList;
import java.util.List;

public class FirstAidAdapter extends RecyclerView.Adapter<ViewHolder> implements SectionIndexer {
    Context context;
    List<FirstAid> Names= new ArrayList<>();
    private ArrayList<Integer> mSectionPositions;
    public FirstAidAdapter(Context context,List<FirstAid>name) {
        this.context=context;
        Names= name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = Names.size(); i < size; i++) {
            String section = String.valueOf(Names.get(i).getName().charAt(0)).toUpperCase();
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
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.text.setText(Names.get(position).getName().trim());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context,FirstAidDetailsActivity.class);
                Log.d("TAG", "onClick: "+holder.text.getText().toString().trim());
                i.putExtra("Name",holder.text.getText().toString().trim());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Names.size();
    }
    public void setFilter(List<FirstAid> nameList)
    {
        Names= new ArrayList<>();
        Names.addAll(nameList);
        notifyDataSetChanged();
    }
}

class   ViewHolder extends RecyclerView.ViewHolder
{
    CardView card;
    TextView text;
    public ViewHolder(View itemView) {
        super(itemView);
        card=(CardView)itemView.findViewById(R.id.card);
        text=(TextView)itemView.findViewById(R.id.text);
    }
}
