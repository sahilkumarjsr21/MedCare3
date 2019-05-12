package com.example.saurabh_pc.medcare3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    private ArrayList<String> myDataset;
    private Context cont;
    Button more;

    public MyAdapter(ArrayList<String> myDataset) {
        this.myDataset=myDataset;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        ViewHolder viewHolder= new ViewHolder(v);
        more=(Button)v.findViewById(R.id.more);
        cont=v.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.view.setText(myDataset.get(position));
        String search="https://www.google.com.eg/search?q=";
        final String cond=myDataset.get(position);
        final String s=search.concat(cond);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent();
                i.setData(Uri.parse(s));
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setAction(Intent.ACTION_VIEW);
                cont.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
       return myDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView view;
        ViewHolder(View v)
        {
            super(v);
            view=(TextView)v.findViewById(R.id.title);
        }
    }
}
