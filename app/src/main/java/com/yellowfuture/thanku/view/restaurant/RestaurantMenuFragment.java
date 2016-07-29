package com.yellowfuture.thanku.view.restaurant;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.Restaurant;
import com.yellowfuture.thanku.model.RestaurantMenu;
import com.yellowfuture.thanku.model.RestaurantOrder;
import com.yellowfuture.thanku.network.controller.RestaurantController;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.view.adapter.RestaurantDetailAdapter;
import com.yellowfuture.thanku.view.adapter.RestaurantDetailMenuAdapter;
import com.yellowfuture.thanku.view.common.BaseFragment;
import com.yellowfuture.thanku.view.profile.ProfileActivity;
import com.yellowfuture.thanku.view.service.BuyActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-07-27.
 */
public class RestaurantMenuFragment extends BaseFragment {
    List<RestaurantMenu> mMenuList;
    Long mRestaurantId;
    RecyclerView mMenuListView;
    RestaurantDetailMenuAdapter mAdapter;
    RestaurantDetailActivity mActivity;
    @Override
    public void init() {
        super.init();
        mActivity = (RestaurantDetailActivity) getActivity();
        mRestaurantId = getArguments().getLong("id");
        initData();
    }

    public void initData(){
        RestaurantController.getInstance(getContext()).findMenu(mAccessToken, mRestaurantId, new Callback<List<RestaurantMenu>>() {
            @Override
            public void onResponse(Call<List<RestaurantMenu>> call, Response<List<RestaurantMenu>> response) {
                if(response.code() == 200) {
                    mMenuList = response.body();
                    initView();
                }
            }

            @Override
            public void onFailure(Call<List<RestaurantMenu>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    @Override
    public void initView() {
        super.initView();
        mMenuListView = (RecyclerView) mView.findViewById(R.id.menuListView);
        mMenuListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RestaurantDetailMenuAdapter(mActivity,mMenuList);

        mMenuListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_restaurant_menu,container,false);
        init();
        return mView;
    }
}
