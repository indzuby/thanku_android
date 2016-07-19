package com.yellowfuture.thanku.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.stmt.query.In;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.view.common.BaseRecyclerAdapter;
import com.yellowfuture.thanku.view.profile.OrderDetailActivity;

/**
 * Created by zuby on 2016. 7. 18..
 */
public class OrderItemAdapter extends BaseRecyclerAdapter {
    Context mContext;

    public OrderItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        itemView.setOnClickListener(this);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, OrderDetailActivity.class);
        ((Activity)mContext).startActivityForResult(intent,1000);
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder

        public ListItemViewHolder(View itemView) {
            super(itemView);
        }
    }

}
