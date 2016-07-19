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
import com.yellowfuture.thanku.view.adapter.OrderItemAdapter;
import com.yellowfuture.thanku.view.adapter.RestaurantItemAdapter;
import com.yellowfuture.thanku.view.basic.BaseFragment;

/**
 * Created by zuby on 2016. 7. 14..
 */
public class ProfileOrderFragment extends BaseFragment {
    RecyclerView orderListView;
    OrderItemAdapter itemAdapter;
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
    public void initView(){
        orderListView = (RecyclerView) mView.findViewById(R.id.orderListView);
    }

    public void init(){
        initView();
        itemAdapter = new OrderItemAdapter();
        orderListView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderListView.setAdapter(itemAdapter);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
