package com.yellowfuture.thanku.network.controller;

import android.content.Context;

import com.yellowfuture.thanku.network.RestApi;
import com.yellowfuture.thanku.network.service.AdvertisementService;

import retrofit2.Retrofit;

/**
 * Created by zuby on 2016-07-21.
 */
public abstract class BaseController {
    protected Context context;
    protected Retrofit retrofit;

    public BaseController(Context context) {
        this.context = context;
        retrofit = RestApi.getInstance(context).getRetrofit();
        initService();
    }
    protected abstract void initService();
}
