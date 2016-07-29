package com.yellowfuture.thanku.view.restaurant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.RestaurantInfo;
import com.yellowfuture.thanku.model.RestaurantMenu;
import com.yellowfuture.thanku.network.controller.RestaurantController;
import com.yellowfuture.thanku.view.common.BaseFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-07-27.
 */
public class RestaurantInfoFragment extends BaseFragment {
    long mInfoId;
    RestaurantInfo mInfo;
    RestaurantDetailActivity mActivity;
    @Override
    public void init() {
        super.init();
        mActivity = (RestaurantDetailActivity) getActivity();
        mInfoId = getArguments().getLong("id");
        initData();
    }
    public void initData(){
        RestaurantController.getInstance(getContext()).findInfo(mAccessToken, mInfoId, new Callback<RestaurantInfo>() {
            @Override
            public void onResponse(Call<RestaurantInfo> call, Response<RestaurantInfo> response) {
                if(response.code() == 200) {
                    mInfo = response.body();
                    initView();
                }
            }

            @Override
            public void onFailure(Call<RestaurantInfo> call, Throwable t) {

            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        TextView notificationView = (TextView) mView.findViewById(R.id.notificaitonTextView);
        TextView descriptionView = (TextView) mView.findViewById(R.id.descriptionTextView);
        TextView businessHoursView = (TextView) mView.findViewById(R.id.businessHoursTextView);
        TextView addressView = (TextView) mView.findViewById(R.id.addressTextView);

        notificationView.setText(mInfo.getNotification());
        descriptionView.setText(mInfo.getDescription());
        businessHoursView.setText(mInfo.getBusinessHours());
        addressView.setText(mInfo.getAddress());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_restaurant_info,container,false);
        init();
        return mView;
    }
}
