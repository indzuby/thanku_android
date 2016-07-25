package com.yellowfuture.thanku.view.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.OrderInfo;
import com.yellowfuture.thanku.model.OrderObject;
import com.yellowfuture.thanku.model.Quick;
import com.yellowfuture.thanku.model.RestaurantOrder;
import com.yellowfuture.thanku.network.controller.OrderController;
import com.yellowfuture.thanku.network.form.OrderObjectForm;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.common.BaseActivity;

import org.joda.time.DateTime;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016. 7. 19..
 */
public class OrderDetailActivity extends BaseActivity{
    long id;
    OrderInfo mOrderInfo;
    TextView mOrderDateTextView;

    TextView mPriceTextView;

    TextView mDeliveryPriceTextView;

    TextView mTotalPriceTextView;

    LinearLayout mOrderListLayout;

    TextView mOrderCommentTextView;

    View mPendingView,mMatchView,mCompleteView;


    @Override
    public void initView() {
        super.initView();

        mPendingView = findViewById(R.id.pendingOvalView);
        mMatchView = findViewById(R.id.matchOvalView);
        mCompleteView = findViewById(R.id.completeOvalView);

        mOrderDateTextView = (TextView) findViewById(R.id.orderDateTextView);
        mPriceTextView = (TextView) findViewById(R.id.priceTextView);
        mDeliveryPriceTextView = (TextView)findViewById(R.id.deliveryPriceTextView);
        mTotalPriceTextView = (TextView) findViewById(R.id.totalPriceTextView);
        mOrderListLayout = (LinearLayout) findViewById(R.id.orderListLayout);
        mOrderCommentTextView = (TextView) findViewById(R.id.orderCommentTextView);

        switch (mOrderInfo.getState()) {
            case PENDING:
                mPendingView.setSelected(true);
                break;
            case MATCH:
                mMatchView.setSelected(true);
                break;
            case COMPLETE:
                mCompleteView.setSelected(true);
                break;
        }

        mOrderDateTextView.setText(new DateTime(mOrderInfo.getUpdatedTime()).toString("yyyy년 M월 d일 a h시 m분"));
        mPriceTextView.setText(Utils.getPriceToString(mOrderInfo.getBasicPrice()));
        mDeliveryPriceTextView.setText(Utils.getPriceToString(mOrderInfo.getDeliveryPrice()));
        mTotalPriceTextView.setText(Utils.getPriceToString(mOrderInfo.getPrice()));

        mOrderListLayout.removeAllViews();

        for(int i = 0; i< mOrderInfo.getGroupItems().size(); i++) {
            View v = LayoutInflater.from(this).inflate(R.layout.item_order_menu,null);
            TextView categoryNameView = (TextView) v.findViewById(R.id.categoryNameTextView);
            LinearLayout itemLayout = (LinearLayout) v.findViewById(R.id.itemLayout);
            List<OrderObjectForm> orderObjectList = mOrderInfo.getGroupItems().get(i);
            OrderObject.OrderType type = null;
            if(i == 0) {
                categoryNameView.setText(getString(R.string.serviceQuick));
                type = OrderObject.OrderType.QUICK;
            }
            else if(i == 1) {
                categoryNameView.setText(getString(R.string.serviceErrand));
                type = OrderObject.OrderType.ERRAND;
            }
            else if(i == 2) {
                categoryNameView.setText(getString(R.string.serviceRestaurant));
                type = OrderObject.OrderType.RESTAURANT;
            }
            else if(i == 3) {
                categoryNameView.setText(getString(R.string.serviceBuy));
                type = OrderObject.OrderType.BUY;
            }
            itemLayout.removeAllViews();
            if(orderObjectList==null || orderObjectList.size()<=0) {
                v.setVisibility(View.GONE);
            }
            for(OrderObjectForm orderObject : orderObjectList) {
                View view =  LayoutInflater.from(this).inflate(R.layout.item_order_menu_detail,null);
                ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                TextView nameView  = (TextView) view.findViewById(R.id.nameTextView);
                TextView priceView = (TextView) view.findViewById(R.id.priceTextView);
                orderObject.setType(type);
                priceView.setText(Utils.getPriceToString(orderObject.getPrice()+orderObject.getAddPrice()));
                switch (type) {
                    case BUY:
                    case ERRAND:
                        thumbnail.setVisibility(View.GONE);
                        nameView.setText(orderObject.getComment());
                        break;
                    case QUICK:
                        Quick quick = (Quick) orderObject.toOrderObject();
                        thumbnail.setVisibility(View.GONE);
                        nameView.setText(quick.getStartAddr()+" -> " +quick.getEndAddr());
                        break;
                    case RESTAURANT:
                        RestaurantOrder restaurantOrder = (RestaurantOrder) orderObject.toOrderObject();
                        thumbnail.setVisibility(View.VISIBLE);
                        Glide.with(this).load(restaurantOrder.getRestaurant().getUrl()).into(thumbnail);
                        nameView.setText(restaurantOrder.getRestaurant().getName());
                        break;
                }
                itemLayout.addView(view);
            }
            mOrderListLayout.addView(v);
        }
        mOrderCommentTextView.setText(mOrderInfo.getComment());

    }
    public void initActionBar(){
        Utils.getActionBar(this, getSupportActionBar(), R.layout.actionbar_default);
        getSupportActionBar().setElevation(0);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.orderDetail));
        findViewById(R.id.back).setOnClickListener(this);

    }
    public void initData(){
        OrderController.getInstance(this).getOrderInfo(mAccessToken, id, new Callback<OrderInfo>() {
            @Override
            public void onResponse(Call<OrderInfo> call, Response<OrderInfo> response) {
                if(response.code() == 200 ){
                    mOrderInfo = response.body();
                    initView();
                }
            }

            @Override
            public void onFailure(Call<OrderInfo> call, Throwable t) {

            }
        });
    }
    @Override
    public void init() {
        super.init();
        id = getIntent().getLongExtra("id",0);
        initData();
        initActionBar();
        findViewById(R.id.cancelButton).setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        init();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
