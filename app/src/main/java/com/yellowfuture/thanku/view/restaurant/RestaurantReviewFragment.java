package com.yellowfuture.thanku.view.restaurant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.Review;
import com.yellowfuture.thanku.network.controller.RestaurantController;
import com.yellowfuture.thanku.view.adapter.ReviewAdapter;
import com.yellowfuture.thanku.view.common.BaseFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-07-27.
 */
public class RestaurantReviewFragment extends BaseFragment {
    Long mRestaurantId;
    RestaurantDetailActivity mActivity;
    List<Review> mReviewList;
    RecyclerView mReviewListView;
    @Override
    public void init() {
        super.init();
        mActivity = (RestaurantDetailActivity) getActivity();
        mRestaurantId = getArguments().getLong("id");
        initData();
    }

    public void initData(){
        RestaurantController.getInstance(getContext()).findReview(mAccessToken, mRestaurantId, new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if(response.code()==200) {
                    mReviewList = response.body();
                    initView();
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    @Override
    public void initView() {
        super.initView();
        mReviewListView = (RecyclerView) mView.findViewById(R.id.reviewListView);
        mReviewListView.setLayoutManager(new LinearLayoutManager(mActivity));
        mReviewListView.setAdapter(new ReviewAdapter(mActivity, mReviewList));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_review,container,false);
        init();
        return mView;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
