package com.yellowfuture.thanku.network.controller;

import android.content.Context;

import com.yellowfuture.thanku.network.form.InquireForm;
import com.yellowfuture.thanku.network.RestApi;
import com.yellowfuture.thanku.network.service.InquireService;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by zuby on 2016. 7. 23..
 */
public class InquireController extends BaseController {

    InquireService inquireService;
    protected static BaseController instance;

    @Override
    protected void initService() {
        if (inquireService == null)
            inquireService = retrofit.create(InquireService.class);
    }

    public InquireController(Context context) {
        super(context);
    }

    public static synchronized InquireController getInstance(Context context) {
        if (instance == null) {
            instance = new InquireController(context);
        }
        return (InquireController) instance;
    }

    public void inquire(String accessToken, InquireForm inquireForm, Callback<Void> callback) {
        Call<Void> call = inquireService.inquire(RestApi.BEARER + accessToken, inquireForm);

        call.enqueue(callback);
    }

}
