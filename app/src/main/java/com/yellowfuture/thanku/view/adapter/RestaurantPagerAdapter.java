package com.yellowfuture.thanku.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.view.profile.ProfileCartFragment;
import com.yellowfuture.thanku.view.profile.ProfileEditFragment;
import com.yellowfuture.thanku.view.profile.ProfileOrderFragment;
import com.yellowfuture.thanku.view.restaurant.RestaurantFragment;

/**
 * Created by zuby on 2016-07-17.
 */
public class RestaurantPagerAdapter extends FragmentStatePagerAdapter {
    int[] tabTitle ={R.string.all,R.string.koFood,R.string.jpFood,R.string.chicken,R.string.pigFood,R.string.nightFood,R.string.soupFood,R.string.lunchFood,R.string.franchise,R.string.cafe,R.string.convenience};
    Fragment[] fragments;
    Context mContext;
    public RestaurantPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        mContext = context;
        fragments = new Fragment[tabTitle.length];
    }

    @Override
    public Fragment getItem(int position) {
        if(fragments[position] == null) {
            fragments[position] = new RestaurantFragment();
        }
        return fragments[position];
    }

    public String getTitle(int position){
        return mContext.getString(tabTitle[position]);
    }
    @Override
    public int getCount() {
        return tabTitle.length;
    }
}
