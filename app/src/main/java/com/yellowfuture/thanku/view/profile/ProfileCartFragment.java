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
import com.yellowfuture.thanku.view.adapter.CartItemAdapter;
import com.yellowfuture.thanku.view.common.BaseFragment;

/**
 * Created by zuby on 2016. 7. 14..
 */
public class ProfileCartFragment extends BaseFragment {
    RecyclerView cartListView;
    CartItemAdapter itemAdapter;
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile_cart,container,false);
        init();
        return mView;
    }
    @Override
    public void initView(){
        cartListView = (RecyclerView) mView.findViewById(R.id.cartListView);
        itemAdapter = new CartItemAdapter();
        cartListView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartListView.setAdapter(itemAdapter);
    }

    @Override
    public void init(){
        super.init();
        initView();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
