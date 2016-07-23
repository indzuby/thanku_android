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
import com.yellowfuture.thanku.model.Restaurant;
import com.yellowfuture.thanku.network.RestApi;
import com.yellowfuture.thanku.view.common.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by zuby on 2016. 7. 18..
 */
public class RestaurantItemAdapter extends BaseRecyclerAdapter {
    Context mContext;
    List<Restaurant> mRestaurants;

    public RestaurantItemAdapter(Context mContext, List<Restaurant> mRestaurants) {
        this.mContext = mContext;
        this.mRestaurants = mRestaurants;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant,parent,false);
        itemView.setOnClickListener(this);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItemViewHolder view = (ListItemViewHolder) holder;

        Restaurant restaurant = mRestaurants.get(position);

        Glide.with(mContext).load(RestApi.url+restaurant.getUrl()).into(view.thumbnailView);
        view.callCountView.setText(restaurant.getCallCount()+"");
        view.commentCountView.setText(restaurant.getCommentCount()+"");
        view.likeCountView.setText(restaurant.getLikeCount()+"");
        view.distanceView.setText((position+1) +".2km");
        view.nameView.setText(restaurant.getName());
        view.hoursView.setText(restaurant.getBusinessHours());
    }

    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }

    @Override
    public void onClick(View v) {

    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder
        ImageView thumbnailView;
        TextView callCountView,commentCountView,likeCountView,distanceView;
        TextView nameView,hoursView;
        public ListItemViewHolder(View view) {

            super(view);
            thumbnailView = (ImageView) view.findViewById(R.id.thumbnail);
            callCountView = (TextView) view.findViewById(R.id.callCountTextView);
            commentCountView = (TextView) view.findViewById(R.id.commentCountTextView);
            likeCountView = (TextView) view.findViewById(R.id.likeCountTextView);
            distanceView = (TextView) view.findViewById(R.id.distanceTextView);
            nameView = (TextView) view.findViewById(R.id.nameTextView);
            hoursView = (TextView) view.findViewById(R.id.businessHoursTextView);
        }
    }

}
