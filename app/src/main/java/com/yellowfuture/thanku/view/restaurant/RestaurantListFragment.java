package com.yellowfuture.thanku.view.restaurant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.domain.Restaurant;
import com.yellowfuture.thanku.network.controller.RestaurantController;
import com.yellowfuture.thanku.view.adapter.RestaurantItemAdapter;
import com.yellowfuture.thanku.view.common.BaseFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-07-17.
 */
public class RestaurantListFragment extends BaseFragment {
    RecyclerView foodListView;
    RestaurantItemAdapter itemAdapter;
    List<Restaurant> mRestaurants;
    long id;
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
    @Override
    public void initView(){
        foodListView = (RecyclerView) mView.findViewById(R.id.foodListView);
        itemAdapter = new RestaurantItemAdapter(getActivity(),mRestaurants);
        foodListView.setLayoutManager(new LinearLayoutManager(getContext()));
        foodListView.setAdapter(itemAdapter);
    }
    @Override
    public void init(){
        super.init();
        initData();
    }
    public void initData(){
        id = getArguments().getLong("id",0L);

        RestaurantController.getInstance(getContext()).findByCategory(mAccessToken,id, new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if(response.code() == 200) {
                    mRestaurants = response.body();
                    initView();
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {

            }
        });
    }
}
