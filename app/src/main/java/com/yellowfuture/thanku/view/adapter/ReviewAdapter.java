package com.yellowfuture.thanku.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.OrderInfo;
import com.yellowfuture.thanku.model.Review;
import com.yellowfuture.thanku.network.RestApi;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.common.BaseRecyclerAdapter;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by zuby on 2016-07-29.
 */
public class ReviewAdapter extends BaseRecyclerAdapter {
    Context mContext;
    List<Review> mReviewList;

    public ReviewAdapter(Context mContext, List<Review> mReviewList) {
        this.mContext = mContext;
        this.mReviewList = mReviewList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == LIST_VIEW_TYPE_HEADER && false) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_header, parent, false);
        }
        else itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        itemView.setOnClickListener(this);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItemViewHolder h = (ListItemViewHolder) holder;
        Review review = mReviewList.get(position);
        h.nameView.setText(review.getWriter().getName());
        h.commentView.setText(review.getComment());
        h.dateBeforeView.setText(Utils.getDateBefore(review.getUpdatedTime()));
        h.scoreTextView.setText(review.getScore()+"");
        Glide.with(mContext).load(RestApi.url+review.getWriter().getProfilePath()).into(h.profileImage);
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    @Override
    public void onClick(View v) {

    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder
        ImageView profileImage;
        TextView nameView;
        TextView commentView;
        TextView dateBeforeView;
        TextView scoreTextView;
        public ListItemViewHolder(View v) {

            super(v);
            profileImage = (ImageView) v.findViewById(R.id.profileImage);
            nameView = (TextView) v.findViewById(R.id.nameTextView);
            commentView = (TextView) v.findViewById(R.id.commentTextView);
            dateBeforeView = (TextView) v.findViewById(R.id.dateBeforeTextView);
            scoreTextView = (TextView) v.findViewById(R.id.scoreTextView);
        }
    }

}
