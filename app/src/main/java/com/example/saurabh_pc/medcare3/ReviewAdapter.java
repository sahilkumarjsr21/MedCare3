package com.example.saurabh_pc.medcare3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private Context mCtx;
    private List<String> reviewList;

    public ReviewAdapter(Context mCtx,List<String> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public ReviewAdapter.ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_layout,parent,false);
        ReviewAdapter.ReviewHolder holder = new ReviewAdapter.ReviewHolder(view);
        TextView txt=(TextView) holder.review.findViewById(R.id.reviewTxt);
        mCtx=view.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewHolder holder, int position) {
        final String review = reviewList.get(position);
        holder.review.setText(review);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class ReviewHolder extends RecyclerView.ViewHolder{

        TextView review;

        public ReviewHolder(View itemView) {
            super(itemView);
            review= itemView.findViewById(R.id.reviewTxt);

        }
    }
}
