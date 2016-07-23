package com.yellowfuture.thanku.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yellowfuture.thanku.model.Category;
import com.yellowfuture.thanku.view.restaurant.RestaurantListFragment;

import java.util.List;

/**
 * Created by zuby on 2016-07-17.
 */
public class RestaurantPagerAdapter extends FragmentStatePagerAdapter {
//    int[] tabTitle ={R.string.all,R.string.koFood,R.string.jpFood,R.string.chicken,R.string.pigFood,R.string.nightFood,R.string.soupFood,R.string.lunchFood,R.string.franchise,R.string.cafe,R.string.convenience};
    List<Category> categoryList;
    Fragment[] fragments;
    public RestaurantPagerAdapter(FragmentManager fm,List<Category> categories) {
        super(fm);
        categoryList = categories;


        Category allCategory = new Category();
        allCategory.setName("전체");
        allCategory.setId(0L);
        categoryList.add(0,allCategory);


        fragments = new Fragment[categoryList.size()];
    }

    @Override
    public Fragment getItem(int position) {
        if(fragments[position] == null) {
            fragments[position] = new RestaurantListFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putLong("id",categoryList.get(position).getId());
        fragments[position].setArguments(bundle);
        return fragments[position];
    }

    public String getTitle(int position){
        return categoryList.get(position).getName();
    }
    @Override
    public int getCount() {
        return categoryList.size();
    }
}
