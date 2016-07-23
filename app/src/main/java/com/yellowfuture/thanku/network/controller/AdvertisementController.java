package com.yellowfuture.thanku.network.controller;

import android.content.Context;

import com.yellowfuture.thanku.model.Advertisement;
import com.yellowfuture.thanku.network.service.AdvertisementService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by zuby on 2016-07-21.
 */
public class AdvertisementController extends BaseController{

    AdvertisementService advertisementService;
    protected static BaseController instance ;

    public AdvertisementController(Context context) {
        super(context);
    }

    public static synchronized AdvertisementController getInstance(Context context) {
        if(instance ==null) {
            instance = new AdvertisementController(context);
        }
        return (AdvertisementController) instance;
    }
    public void findByType(Advertisement.AdvertisementType type, Callback<List<Advertisement>> callback) {
        Call<List<Advertisement>> call = advertisementService.findByType(type);
        call.enqueue(callback);
    }

    @Override
    protected void initService() {
        if(advertisementService == null)
            advertisementService = retrofit.create(AdvertisementService.class);
    }
}
