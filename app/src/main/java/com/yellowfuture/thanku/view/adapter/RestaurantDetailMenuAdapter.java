package com.yellowfuture.thanku.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.control.GpsControl;
import com.yellowfuture.thanku.model.Restaurant;
import com.yellowfuture.thanku.model.RestaurantMenu;
import com.yellowfuture.thanku.network.RestApi;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.common.BaseRecyclerAdapter;
import com.yellowfuture.thanku.view.restaurant.RestaurantMenuSelectPopup;

import java.util.List;

/**
 * Created by zuby on 2016-07-27.
 */
public class RestaurantDetailMenuAdapter extends BaseRecyclerAdapter{
    Activity mActivity;
    List<RestaurantMenu> mMenuList;

    public RestaurantDetailMenuAdapter(Activity activity, List<RestaurantMenu> mMenuList) {
        this.mActivity = activity;
        this.mMenuList = mMenuList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == LIST_VIEW_TYPE_HEADER) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_menu_group, parent, false);
            itemView.setOnClickListener(this);
            return new ListHeaderItemViewHolder(itemView);
        }
        else{
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_menu_child, parent, false);
            itemView.setOnClickListener(this);
            return new ListItemViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeader(position)) {
            ListHeaderItemViewHolder view = (ListHeaderItemViewHolder) holder;
            view.sortButton.setOnClickListener(this);
        }else {
            ListItemViewHolder view = (ListItemViewHolder) holder;
            RestaurantMenu menu = mMenuList.get(position-1);
            view.itemView.setTag(menu);
            view.itemView.setOnClickListener(this);
            view.nameView.setText(menu.getName());
            view.priceView.setText(Utils.getPriceToString(menu.getPrice()));
            view.commentView.setText(menu.getComment());
        }

    }

    @Override
    public int getItemCount() {
        return mMenuList.size()+1;
    }

    @Override
    public void onClick(View v) {
        RestaurantMenu menu = (RestaurantMenu)v.getTag();
        new RestaurantMenuSelectPopup(mActivity,menu).show();
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder
        TextView nameView;
        TextView commentView,priceView;
        public ListItemViewHolder(View view) {

            super(view);
            nameView = (TextView) view.findViewById(R.id.nameTextView);
            commentView = (TextView) view.findViewById(R.id.commentTextView);
            priceView = (TextView) view.findViewById(R.id.priceTextView);
        }
    }
    class ListHeaderItemViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder
        TextView categoryNameView;
        ImageView sortButton;
        public ListHeaderItemViewHolder(View view) {

            super(view);
            categoryNameView = (TextView) view.findViewById(R.id.menuCategoryView);
            sortButton = (ImageView) view.findViewById(R.id.sortButton);
        }
    }

}
