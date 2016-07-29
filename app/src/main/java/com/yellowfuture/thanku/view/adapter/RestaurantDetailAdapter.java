package com.yellowfuture.thanku.view.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.Restaurant;
import com.yellowfuture.thanku.view.restaurant.RestaurantInfoFragment;
import com.yellowfuture.thanku.view.restaurant.RestaurantMenuFragment;
import com.yellowfuture.thanku.view.restaurant.RestaurantReviewFragment;

import lombok.Getter;

/**
 * Created by zuby on 2016-07-27.
 */
public class RestaurantDetailAdapter extends FragmentStatePagerAdapter {

    Context mContext;
    Restaurant mRestaurant;
    @Getter
    Fragment[] fragments;

    public RestaurantDetailAdapter(FragmentManager fm, Context mContext, Restaurant restaurant) {
        super(fm);
        this.mContext = mContext;
        this.mRestaurant = restaurant;
        fragments = new Fragment[3];
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putLong("id",mRestaurant.getId());
        if(fragments[position] == null) {

            if (position == 0) {
                fragments[position] = new RestaurantMenuFragment();
            } else if (position == 1) {
                bundle.putLong("id",mRestaurant.getInformation().getId());
                fragments[position] = new RestaurantInfoFragment();

            } else if (position == 2) {
                fragments[position] = new RestaurantReviewFragment();

            }
        }
        fragments[position].setArguments(bundle);
        return fragments[position];
    }
    public String getTitle(int position){
        if(position ==0 )
            return mContext.getString(R.string.menu);
        else if(position ==1 )
            return mContext.getString(R.string.information);
        else if(position ==2 )
            return mContext.getString(R.string.review,mRestaurant.getReviewCount());
        return "";
    }

    @Override
    public int getCount() {
        return 3;
    }
}
