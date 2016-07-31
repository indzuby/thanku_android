package com.yellowfuture.thanku.view.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.Restaurant;
import com.yellowfuture.thanku.model.RestaurantOrderMenu;
import com.yellowfuture.thanku.network.controller.RestaurantController;
import com.yellowfuture.thanku.network.form.OrderObjectForm;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.adapter.ImagePagerAdapter;
import com.yellowfuture.thanku.view.adapter.RestaurantDetailAdapter;
import com.yellowfuture.thanku.view.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-07-27.
 */
public class RestaurantDetailActivity extends BaseActivity {
    long mId;

    Restaurant mRestaurant;
    TextView mCallCountView;
    TextView mScoreView;
    TextView mLikeCountView;
    ViewPager mImageViewPager;
    ViewPager mBodyViewPager;
    LinearLayout mOverLayout;
    TabLayout mTabs;
    RestaurantDetailAdapter mRestaurantDetailAdapter;
    int totalPrice = 0;

    List<RestaurantOrderMenu> mOrderMenuList = new ArrayList<>();

    public void initActionBar() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(mRestaurant.getName());
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.sortButton).setVisibility(View.GONE);
    }

    @Override
    public void initView() {
        super.initView();
        initActionBar();
        mCallCountView = (TextView) findViewById(R.id.callCountTextView);
        mScoreView = (TextView) findViewById(R.id.scoreTextView);
        mLikeCountView = (TextView) findViewById(R.id.likeCountTextView);
        mImageViewPager = (ViewPager) findViewById(R.id.imageViewPager);
        mOverLayout = (LinearLayout) findViewById(R.id.ovalLayout);
        mBodyViewPager = (ViewPager) findViewById(R.id.restaurantViewPager);
        mTabs = (TabLayout) findViewById(R.id.tabs);
        findViewById(R.id.callButton).setOnClickListener(this);



        mCallCountView.setText("주문수 " + mRestaurant.getCallCount());
        mScoreView.setText("추천 " + mRestaurant.getAvgScore() + "점");
        mLikeCountView.setText("좋아요 " + mRestaurant.getLikeCount());

        mImageViewPager.setAdapter(new ImagePagerAdapter<>(this, mRestaurant.getImageList()));
        if(mRestaurant.getImageList()!=null && mRestaurant.getImageList().size()>0) {
            Utils.setOvalContainer(this, mOverLayout, mRestaurant.getImageList().size());
            Utils.selectOval(mOverLayout, mImageViewPager, 0);
        }

        mRestaurantDetailAdapter = new RestaurantDetailAdapter(getSupportFragmentManager(),this,mRestaurant);
        mBodyViewPager.setAdapter(mRestaurantDetailAdapter);
        mTabs.setupWithViewPager(mBodyViewPager);
        for(int i = 0; i< mTabs.getTabCount();i++) {
            TabLayout.Tab tab = mTabs.getTabAt(i);
            tab.setText(mRestaurantDetailAdapter.getTitle(i));
        }

        findViewById(R.id.addCartButton).setOnClickListener(this);

    }

    public void initData(){
        mId = getIntent().getLongExtra("id",0);
        RestaurantController.getInstance(this).find(mAccessToken, mId, new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                mRestaurant = response.body();
                initView();
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });
    }
    public void addMenuInCart(RestaurantOrderMenu menu){
        if(mOrderMenuList.indexOf(menu)!=-1) {
            RestaurantOrderMenu menu2 = mOrderMenuList.get(mOrderMenuList.indexOf(menu));
            menu2.setCount(menu.getCount()+menu2.getCount());
            menu2.setPrice(menu2.getPrice()+menu.getPrice());
        }else
            mOrderMenuList.add(menu);

        totalPrice += menu.getPrice();
    }
    public void deleteMenuCart(RestaurantOrderMenu menu){
        mOrderMenuList.remove(menu);
        totalPrice -= menu.getPrice();
    }
    public void addCart(OrderObjectForm form){

    }
    @Override
    public void init() {
        super.init();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        init();
    }

    public void addCart(){
        if(mOrderMenuList.size()<=0) {
            Toast.makeText(this,"메뉴를 선택해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this,RestaurantCartActivity.class);
        Utils.setRestaurant(mRestaurant);
        Utils.setOrderMenuList(mOrderMenuList);
        intent.putExtra("price",totalPrice);
        startActivityForResult(intent, CodeDefinition.REQUEST_RESTAURANT_CART);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.addCartButton) {
            addCart();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == CodeDefinition.REQUEST_RESTAURANT_CART)
            finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
