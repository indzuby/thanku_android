package com.yellowfuture.thanku.view.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.OrderObject;
import com.yellowfuture.thanku.network.controller.OrderController;
import com.yellowfuture.thanku.network.controller.UserController;
import com.yellowfuture.thanku.network.form.OrderObjectForm;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.view.adapter.CartItemAdapter;
import com.yellowfuture.thanku.view.common.BaseFragment;
import com.yellowfuture.thanku.view.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016. 7. 14..
 */
public class ProfileCartFragment extends BaseFragment {
    RecyclerView cartListView;
    CartItemAdapter itemAdapter;
    List<OrderObjectForm> orderObjectList;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.addOrderButton) {
            getActivity().finish();
        }else if(v.getId() == R.id.buyOrderButton) {
            buyOrder();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile_cart,container,false);
        init();
        return mView;
    }
    @Override
    public void initView(){
        super.initView();
        cartListView = (RecyclerView) mView.findViewById(R.id.cartListView);
        itemAdapter = new CartItemAdapter(getActivity(),orderObjectList);
        cartListView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartListView.setAdapter(itemAdapter);
    }
    public void initData(){
        UserController.getInstance(getContext()).cartList(mAccessToken, new Callback<List<OrderObjectForm>>() {
            @Override
            public void onResponse(Call<List<OrderObjectForm>> call, Response<List<OrderObjectForm>> response) {
                if(response.code() == 200) {
                    orderObjectList = response.body();
                    initView();
                }
            }

            @Override
            public void onFailure(Call<List<OrderObjectForm>> call, Throwable t) {

            }
        });
    }
    public void buyOrder(){
        OrderController.getInstance(getContext()).ordering(mAccessToken, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getContext(),"주문이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra(CodeDefinition.PROFILE_START_PARAM, CodeDefinition.PROFILE_ORDER_CODE);
                getActivity().startActivityForResult(intent,CodeDefinition.REQUEST_PROFILE_CODE);
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
    @Override
    public void init(){
        super.init();
        initData();

        mView.findViewById(R.id.addOrderButton).setOnClickListener(this);

        mView.findViewById(R.id.buyOrderButton).setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
