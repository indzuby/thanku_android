package com.yellowfuture.thanku.view.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.OrderInfo;
import com.yellowfuture.thanku.model.OrderObject;
import com.yellowfuture.thanku.model.Quick;
import com.yellowfuture.thanku.model.RestaurantOrder;
import com.yellowfuture.thanku.model.RestaurantOrderMenu;
import com.yellowfuture.thanku.model.Review;
import com.yellowfuture.thanku.network.controller.OrderController;
import com.yellowfuture.thanku.network.form.OrderObjectForm;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.SessionUtils;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.common.BaseActivity;

import org.joda.time.DateTime;

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

    int canComplete = 0;

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

        mPendingView.setSelected(false);
        mMatchView.setSelected(false);
        mCompleteView.setSelected(false);

        switch (mOrderInfo.getState()) {
            case PENDING:
                mPendingView.setSelected(true);
                canComplete = 0;
                break;
            case MATCH:
                mMatchView.setSelected(true);
                canComplete = 1;
                break;
            case COMPLETE:
                mCompleteView.setSelected(true);
                canComplete = 2;
                break;
        }

        mOrderDateTextView.setText(new DateTime(mOrderInfo.getUpdatedTime()).toString("yyyy년 M월 d일 a h시 m분"));
        mPriceTextView.setText(Utils.getPriceToString(mOrderInfo.getBasicPrice()));
        mDeliveryPriceTextView.setText(Utils.getPriceToString(mOrderInfo.getDeliveryPrice()));
        mTotalPriceTextView.setText(Utils.getPriceToString(mOrderInfo.getPrice()));

        mOrderListLayout.removeAllViews();

        for(OrderObjectForm orderFrom : mOrderInfo.getItems()) {
            View v = LayoutInflater.from(this).inflate(R.layout.item_order_menu, null);
            TextView categoryNameView = (TextView) v.findViewById(R.id.categoryNameTextView);
            LinearLayout itemLayout = (LinearLayout) v.findViewById(R.id.itemLayout);
            OrderObjectForm orderObject = orderFrom;
            OrderObject.OrderType type = OrderObject.OrderType.BUY;
            if (orderObject.getObjectType().equals("Q")) {
                categoryNameView.setText(getString(R.string.serviceQuick));
                type = OrderObject.OrderType.QUICK;
            } else if (orderObject.getObjectType().equals("E")) {
                categoryNameView.setText(getString(R.string.serviceErrand));
                type = OrderObject.OrderType.ERRAND;
            } else if (orderObject.getObjectType().equals("B")) {
                categoryNameView.setText(getString(R.string.serviceBuy));
                type = OrderObject.OrderType.BUY;
            } else if (orderObject.getObjectType().equals("R")) {
                RestaurantOrder order = (RestaurantOrder) orderObject.toOrderObject(RestaurantOrder.class);
                categoryNameView.setText(order.getRestaurant().getName());
                type = OrderObject.OrderType.RESTAURANT;
            }
            View reviewButton = v.findViewById(R.id.reviewButton);

            Review review = orderFrom.getReview();
            if(review==null) review = new Review();
            review.setOrderObjectId(orderObject.getId());
            reviewButton.setOnClickListener(new ReviewClickListener(review,categoryNameView.getText().toString()));
            setItemLayout(orderObject, itemLayout, type);
            mOrderListLayout.addView(v);
        }
        mOrderCommentTextView.setText(mOrderInfo.getComment());
        if(canComplete==1){
            findViewById(R.id.completeButton).setVisibility(View.VISIBLE);
            findViewById(R.id.cancelButton).setVisibility(View.GONE);
            findViewById(R.id.completeButton).setOnClickListener(this);
        }else if(canComplete==0) {
            findViewById(R.id.completeButton).setVisibility(View.GONE);
            findViewById(R.id.cancelButton).setVisibility(View.VISIBLE);
            findViewById(R.id.cancelButton).setOnClickListener(this);
        }else {
            findViewById(R.id.completeButton).setVisibility(View.GONE);
            findViewById(R.id.cancelButton).setVisibility(View.GONE);

        }
    }

    private void setItemLayout(OrderObjectForm orderObject, LinearLayout layout, OrderObject.OrderType type) {
        layout.removeAllViews();
        if (type != OrderObject.OrderType.RESTAURANT) {
            View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_order_menu_detail, null);
            ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            TextView nameView = (TextView) view.findViewById(R.id.nameTextView);
            TextView priceView = (TextView) view.findViewById(R.id.priceTextView);
            orderObject.setType(type);
            priceView.setText(Utils.getPriceToString(orderObject.getPrice() + orderObject.getAddPrice()));
            nameView.setText(orderObject.getComment());
            thumbnail.setVisibility(View.GONE);
            if (type == OrderObject.OrderType.QUICK) {
                Quick quick = (Quick) orderObject.toOrderObject(Quick.class);
                nameView.setText(quick.getStartAddr() + " -> " + quick.getEndAddr());
            }
            layout.addView(view);
        } else {
            RestaurantOrder restaurantOrder = (RestaurantOrder) orderObject.toOrderObject(RestaurantOrder.class);
            for (RestaurantOrderMenu orderMenu : restaurantOrder.getMenuList()) {
                View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_order_menu_detail, null);
                ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                TextView countView = (TextView) view.findViewById(R.id.menuCountView);
                TextView nameView = (TextView) view.findViewById(R.id.nameTextView);
                TextView priceView = (TextView) view.findViewById(R.id.priceTextView);
                priceView.setText(Utils.getPriceToString(orderMenu.getPrice()));
                nameView.setText(orderMenu.getRestaurantMenu().getName());
                countView.setVisibility(View.VISIBLE);
                countView.setText(orderMenu.getCount()+"개");
                Glide.with(getBaseContext()).load(orderMenu.getRestaurantMenu().getUrl()).into(thumbnail);
                layout.addView(view);
            }
        }
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
    class ReviewClickListener implements View.OnClickListener{
        Review review;
        String name;

        public ReviewClickListener(Review review, String name) {
            this.review = review;
            this.name = name;
        }

        @Override
        public void onClick(View v) {
            ReviewPopup reviewPopup = new ReviewPopup(OrderDetailActivity.this,review,name);
            reviewPopup.show();
        }
    }
    @Override
    public void init() {
        super.init();
        id = getIntent().getLongExtra("id",0);
        initData();
        initActionBar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        init();
    }
    public void setComplete(){
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);

        alertDlg.setTitle("해당 주문을 완료하시겠습니까?");
        alertDlg.setPositiveButton("예", new DialogInterface.OnClickListener() { // 확인 버튼
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                OrderController.getInstance(getBaseContext()).orderComplete(mAccessToken, mOrderInfo.getId(), new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 204) {
                            Toast.makeText(getBaseContext(),"완료 되었습니다.",Toast.LENGTH_SHORT).show();
                            initData();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });
        alertDlg.setNegativeButton("아니오", new DialogInterface.OnClickListener() { // 취소 버튼
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                dialog.cancel();
            }
        });
        AlertDialog alert = alertDlg.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.completeButton) {
            setComplete();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
