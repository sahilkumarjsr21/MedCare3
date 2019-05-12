package com.example.saurabh_pc.medcare3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class imageAdapter extends RecyclerView.Adapter<imageAdapter.imgHolder>{

    private Context mCtx;
    private List<imagePres> imgList;

    public imageAdapter(Context mCtx, List<imagePres> imgList) {
//        this.mCtx = mCtx;
        this.imgList = imgList;
    }

    @Override
    public imageAdapter.imgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item,parent,false);
        imageAdapter.imgHolder holder = new imageAdapter.imgHolder(view);
        mCtx=view.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(imageAdapter.imgHolder holder, int position) {
        final imagePres details = imgList.get(position);
        holder.date.setText(details.getDate());
        Bitmap bmp = BitmapFactory.decodeByteArray(details.getImage(),0, details.getImage().length);
        holder.imageView.setImageBitmap(bmp);
        //holder.imageView.setImageBitmap(details.getImage());

    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }

    class imgHolder extends RecyclerView.ViewHolder{

        TextView date;
        ImageView imageView;

        public imgHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            date = itemView.findViewById(R.id.date_img);

        }
    }
}
