package com.yellowfuture.thanku.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.OrderObject;
import com.yellowfuture.thanku.model.Quick;
import com.yellowfuture.thanku.model.RestaurantOrder;
import com.yellowfuture.thanku.network.form.OrderObjectForm;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.common.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by zuby on 2016. 7. 18..
 */
public class CartItemAdapter extends BaseRecyclerAdapter {

    Context mContext;
    List<List<OrderObjectForm>> orderObjectList;

    public CartItemAdapter(Context mContext, List<List<OrderObjectForm>> orderObjectList) {
        this.mContext = mContext;
        this.orderObjectList = orderObjectList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        itemView.setOnClickListener(this);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItemViewHolder h = (ListItemViewHolder) holder;
        List<OrderObjectForm> orderObjects = orderObjectList.get(position);
        OrderObject.OrderType type = OrderObject.OrderType.BUY;
        if(position == 0) {
            h.categoryNameView.setText(mContext.getString(R.string.serviceQuick));
            type = OrderObject.OrderType.QUICK;
        }
        else if(position == 1) {
            h.categoryNameView.setText(mContext.getString(R.string.serviceErrand));
            type = OrderObject.OrderType.ERRAND;
        }
        else if(position == 2) {
            h.categoryNameView.setText(mContext.getString(R.string.serviceRestaurant));
            type = OrderObject.OrderType.RESTAURANT;
        }
        else if(position == 3) {
            h.categoryNameView.setText(mContext.getString(R.string.serviceBuy));
            type = OrderObject.OrderType.BUY;
        }
        h.itemLayout.setVisibility(View.VISIBLE);
        h.categoryNameView.setVisibility(View.VISIBLE);
        if(orderObjects.size()<=0) {
            h.categoryNameView.setVisibility(View.GONE);
            h.itemLayout.setVisibility(View.GONE);
        }
        h.itemLayout.removeAllViews();
        for(OrderObjectForm orderObject : orderObjects) {
            View view =  LayoutInflater.from(mContext).inflate(R.layout.item_cart_detail,null);
            ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            TextView nameView  = (TextView) view.findViewById(R.id.nameTextView);
            TextView priceView = (TextView) view.findViewById(R.id.priceTextView);
            orderObject.setType(type);
            priceView.setText(Utils.getPriceToString(orderObject.getPrice()+orderObject.getAddPrice()));
            switch (type) {
                case BUY:
                case ERRAND:
                    thumbnail.setVisibility(View.GONE);
                    nameView.setText(orderObject.getComment());
                    break;
                case QUICK:
                    Quick quick = (Quick) orderObject.toOrderObject();
                    thumbnail.setVisibility(View.GONE);
                    nameView.setText(quick.getStartAddr()+" -> " +quick.getEndAddr());
                    break;
                case RESTAURANT:
                    RestaurantOrder restaurantOrder = (RestaurantOrder) orderObject.toOrderObject();
                    thumbnail.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(restaurantOrder.getRestaurant().getUrl()).into(thumbnail);
                    nameView.setText(restaurantOrder.getRestaurant().getName());
                    break;
            }
            h.itemLayout.addView(view);
        }
    }

    @Override
    public int getItemCount() {
        return orderObjectList.size();
    }

    @Override
    public void onClick(View v) {

    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder
        TextView categoryNameView;
        LinearLayout itemLayout;
        View v;
        public ListItemViewHolder(View v) {
            super(v);
            this.v = v;
            categoryNameView = (TextView) v.findViewById(R.id.categoryNameTextView);
            itemLayout = (LinearLayout) v.findViewById(R.id.itemLayout);
        }
    }

}
