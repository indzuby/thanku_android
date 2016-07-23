package com.yellowfuture.thanku.network.controller;

import android.content.Context;

import com.yellowfuture.thanku.model.OrderInfo;
import com.yellowfuture.thanku.model.OrderObject;
import com.yellowfuture.thanku.network.RestApi;
import com.yellowfuture.thanku.network.form.OrderObjectForm;
import com.yellowfuture.thanku.network.service.OrderService;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by zuby on 2016. 7. 24..
 */
public class OrderController extends BaseController{
    OrderService orderService;
    private static OrderController instance;
    @Override
    protected void initService() {
        if(orderService == null)
            orderService = retrofit.create(OrderService.class);
    }

    public static synchronized OrderController getInstance(Context context) {
        if (instance == null) {
            instance = new OrderController(context);
        }
        return (OrderController) instance;
    }

    public OrderController(Context context) {
        super(context);
    }
    public void getOrderInfo(String accessToken, Long id, Callback<OrderInfo> callback){
        Call<OrderInfo> call = orderService.getOrderInfo(RestApi.BEARER+accessToken,id);
        call.enqueue(callback);

    }
    public void addOrder(String accessToken, OrderObjectForm form, Callback<OrderObject> callback) {
        Call<OrderObject> call = orderService.addOrder(RestApi.BEARER+accessToken,form);
        call.enqueue(callback);

    }

    public void ordering(String accessToken ,Callback<Void> callback) {
        Call<Void> call = orderService.ordering(RestApi.BEARER+accessToken);
        call.enqueue(callback);
    }

    public void updateOrder(String accessToken, Long id, OrderObjectForm form, Callback<OrderObject> callback){
        Call<OrderObject> call = orderService.updateOrder(RestApi.BEARER+accessToken,id,form);
        call.enqueue(callback);

    }
    public void remove(String accessToken,Long id, Callback<Void> callback){
        Call<Void> call = orderService.remove(RestApi.BEARER+accessToken,id);
        call.enqueue(callback);

    }
}
