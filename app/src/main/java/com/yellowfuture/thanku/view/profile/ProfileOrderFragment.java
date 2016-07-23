package com.yellowfuture.thanku.view.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.OrderInfo;
import com.yellowfuture.thanku.model.OrderObject;
import com.yellowfuture.thanku.network.controller.UserController;
import com.yellowfuture.thanku.network.form.OrderObjectForm;
import com.yellowfuture.thanku.view.adapter.OrderItemAdapter;
import com.yellowfuture.thanku.view.common.BaseFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016. 7. 14..
 */
public class ProfileOrderFragment extends BaseFragment {
    RecyclerView orderListView;
    OrderItemAdapter itemAdapter;
    List<OrderInfo> orderInfoList;
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile_order,container,false);
        init();

        return mView;
    }
    @Override
    public void initView(){
        super.initView();
        orderListView = (RecyclerView) mView.findViewById(R.id.orderListView);
        itemAdapter = new OrderItemAdapter(getActivity(),orderInfoList);
        orderListView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderListView.setAdapter(itemAdapter);
    }

    public void initData(){
        UserController.getInstance(getContext()).orderList(mAccessToken, new Callback<List<OrderInfo>>() {
            @Override
            public void onResponse(Call<List<OrderInfo>> call, Response<List<OrderInfo>> response) {
                if(response.code() == 200) {
                    orderInfoList = response.body();
                    initView();
                }
            }

            @Override
            public void onFailure(Call<List<OrderInfo>> call, Throwable t) {

            }
        });
    }
    @Override
    public void init(){
        super.init();
        initData();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
