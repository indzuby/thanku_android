package com.yellowfuture.thanku.network.service;

import com.yellowfuture.thanku.domain.User;
import com.yellowfuture.thanku.network.form.LoginForm;
import com.yellowfuture.thanku.network.form.LoginResponseForm;
import com.yellowfuture.thanku.network.form.SignUpForm;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by zuby on 2016-07-21.
 */

public interface UserService {
    @POST("/api/user")
    Call<User> signUpByEmail(@Body SignUpForm user);


    @FormUrlEncoded
    @POST("/oauth/token")
    Call<LoginResponseForm> login(@Header("Authorization")String authorization, @FieldMap Map<String,String> user) ;
}
