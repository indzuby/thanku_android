package com.yellowfuture.thanku.view.restaurant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.view.adapter.RestaurantItemAdapter;
import com.yellowfuture.thanku.view.common.BaseFragment;

/**
 * Created by zuby on 2016-07-17.
 */
public class RestaurantFragment extends BaseFragment {
    RecyclerView foodListView;
    RestaurantItemAdapter itemAdapter;
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_restaurant_list,container,false);
        init();
        return mView;
    }
    public void initView(){
        foodListView = (RecyclerView) mView.findViewById(R.id.foodListView);
    }
    public void init(){
        initView();
        itemAdapter = new RestaurantItemAdapter();
        foodListView.setLayoutManager(new LinearLayoutManager(getContext()));
        foodListView.setAdapter(itemAdapter);

    }
}
