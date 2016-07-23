package com.yellowfuture.thanku.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.view.profile.ProfileCartFragment;
import com.yellowfuture.thanku.view.profile.ProfileEditFragment;
import com.yellowfuture.thanku.view.profile.ProfileOrderFragment;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zuby on 2016. 7. 14..
 */
public class ProfilePagerAdapter extends FragmentStatePagerAdapter {

    Context mContext;
    int[] tabTitle = {R.string.profileEdit, R.string.profileOrder, R.string.profileCart};
    @Getter
    Fragment[] fragments;

    public ProfilePagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
        fragments = new Fragment[tabTitle.length];
    }

    @Override
    public Fragment getItem(int position) {

        if(fragments[position] == null) {

            if (position == 0) {
                fragments[position] = new ProfileEditFragment();
            } else if (position == 1) {
                fragments[position] = new ProfileOrderFragment();

            } else if (position == 2) {
                fragments[position] = new ProfileCartFragment();

            }
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
