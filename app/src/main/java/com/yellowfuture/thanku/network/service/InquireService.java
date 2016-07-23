package com.yellowfuture.thanku.network.service;

import com.yellowfuture.thanku.network.form.InquireForm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by zuby on 2016. 7. 23..
 */
public interface InquireService {

    @POST("/api/inquire")
    Call<Void> inquire(@Header("Authorization") String authorization, @Body InquireForm inquireForm);
}
