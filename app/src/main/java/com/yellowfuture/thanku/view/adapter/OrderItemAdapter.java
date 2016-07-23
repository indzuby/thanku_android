package com.yellowfuture.thanku.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.OrderInfo;
import com.yellowfuture.thanku.view.common.BaseRecyclerAdapter;
import com.yellowfuture.thanku.view.profile.OrderDetailActivity;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

/**
 * Created by zuby on 2016. 7. 18..
 */
public class OrderItemAdapter extends BaseRecyclerAdapter {
    Context mContext;
    List<OrderInfo> orderInfoList;

    public OrderItemAdapter(Context mContext, List<OrderInfo> orderInfoList) {
        this.mContext = mContext;
        this.orderInfoList = orderInfoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItemViewHolder h = (ListItemViewHolder) holder;
        OrderInfo info = orderInfoList.get(position);
        h.thumbnail.setVisibility(View.GONE);
        DateTime time = new DateTime(info.getUpdatedTime());
        h.nameView.setText(time.toString("M월 dd일 a h시 m분") + "에 " + info.getCount() + "건");
        h.commentView.setText(info.getComment());
        h.priceView.setText(info.getPrice() + "원");
        if (Days.daysBetween(time, new DateTime()).getDays() <= 0)
            if (Hours.hoursBetween(time, new DateTime()).getHours() <= 0)
                h.dateBeforeView.setText(Minutes.minutesBetween(time, new DateTime()).getMinutes() + "분 전");
            else
                h.dateBeforeView.setText(Hours.hoursBetween(time, new DateTime()).getHours() + "시간 전");
        else
            h.dateBeforeView.setText(Days.daysBetween(time, new DateTime()).getDays() + "일 전");
        h.itemView.setTag(info.getId());
        h.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return orderInfoList.size();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, OrderDetailActivity.class);
        intent.putExtra("id", (Long) v.getTag());
        ((Activity) mContext).startActivityForResult(intent, 1000);
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder
        ImageView thumbnail;
        TextView nameView;
        TextView commentView;
        TextView priceView;
        TextView dateBeforeView;

        public ListItemViewHolder(View v) {

            super(v);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            nameView = (TextView) v.findViewById(R.id.nameTextView);
            commentView = (TextView) v.findViewById(R.id.commentTextView);
            priceView = (TextView) v.findViewById(R.id.priceTextView);
            dateBeforeView = (TextView) v.findViewById(R.id.dateBeforeTextView);
        }
    }

}
