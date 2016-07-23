package com.yellowfuture.thanku.network.service;

import com.yellowfuture.thanku.model.Advertisement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zuby on 2016-07-21.
 */
public interface AdvertisementService {
    @GET("/api/adv")
    Call<List<Advertisement>> findByType(@Query("type") Advertisement.AdvertisementType type);
}
