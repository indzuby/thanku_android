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
import com.yellowfuture.thanku.model.Restaurant;
import com.yellowfuture.thanku.model.RestaurantMenu;
import com.yellowfuture.thanku.model.RestaurantOrder;
import com.yellowfuture.thanku.model.RestaurantOrderMenu;
import com.yellowfuture.thanku.network.form.OrderObjectForm;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.common.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by zuby on 2016. 7. 18..
 */
public class CartItemAdapter extends BaseRecyclerAdapter {

    Context mContext;
    List<OrderObjectForm> orderObjectList;

    public CartItemAdapter(Context mContext, List<OrderObjectForm> orderObjectList) {
        this.mContext = mContext;
        this.orderObjectList = orderObjectList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        itemView.setOnClickListener(this);
        return new ListItemViewHolder(itemView);
    }

    private void setItemLayout(OrderObjectForm orderObject, LinearLayout layout, OrderObject.OrderType type) {
        layout.removeAllViews();
        if (type != OrderObject.OrderType.RESTAURANT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_cart_detail, null);
            ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            TextView nameView = (TextView) view.findViewById(R.id.nameTextView);
            TextView priceView = (TextView) view.findViewById(R.id.priceTextView);
            orderObject.setType(type);
            priceView.setText(Utils.getPriceToString(orderObject.getPrice() + orderObject.getAddPrice()));
            nameView.setText(orderObject.getComment());
            thumbnail.setVisibility(View.GONE);
            if (type == OrderObject.OrderType.QUICK) {
                Quick quick = (Quick) orderObject.toOrderObject(Quick.class);
                nameView.setText(quick.getStartAddr() + " -> " + quick.getEndAddr());
            }
            layout.addView(view);
        } else {
            RestaurantOrder restaurantOrder = (RestaurantOrder) orderObject.toOrderObject(RestaurantOrder.class);
            for (RestaurantOrderMenu orderMenu : restaurantOrder.getMenus()) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_cart_detail, null);
                ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                TextView nameView = (TextView) view.findViewById(R.id.nameTextView);
                TextView priceView = (TextView) view.findViewById(R.id.priceTextView);
                priceView.setText(Utils.getPriceToString(orderMenu.getPrice()));
                nameView.setText(orderMenu.getMenu().getName() +" "+orderMenu.getCount()+"개");
                Glide.with(mContext).load(orderMenu.getMenu().getUrl()).into(thumbnail);
                layout.addView(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItemViewHolder h = (ListItemViewHolder) holder;
        OrderObjectForm orderObject = orderObjectList.get(position);
        OrderObject.OrderType type = OrderObject.OrderType.BUY;
        if (orderObject.getObjectType().equals("Q")) {
            h.categoryNameView.setText(mContext.getString(R.string.serviceQuick));
            type = OrderObject.OrderType.QUICK;
        } else if (orderObject.getObjectType().equals("E")) {
            h.categoryNameView.setText(mContext.getString(R.string.serviceErrand));
            type = OrderObject.OrderType.ERRAND;
        } else if (orderObject.getObjectType().equals("B")) {
            h.categoryNameView.setText(mContext.getString(R.string.serviceBuy));
            type = OrderObject.OrderType.BUY;
        } else if (orderObject.getObjectType().equals("R")) {
            RestaurantOrder order = (RestaurantOrder) orderObject.toOrderObject(RestaurantOrder.class);
            h.categoryNameView.setText(order.getRestaurant().getName());
            type = OrderObject.OrderType.RESTAURANT;
        }
        setItemLayout(orderObject, h.itemLayout, type);

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
