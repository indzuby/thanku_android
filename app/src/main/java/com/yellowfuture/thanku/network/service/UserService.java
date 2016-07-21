package com.yellowfuture.thanku.network.service;

import com.yellowfuture.thanku.domain.User;
import com.yellowfuture.thanku.network.form.LoginForm;
import com.yellowfuture.thanku.network.form.LoginResponseForm;
import com.yellowfuture.thanku.network.form.SignUpForm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by zuby on 2016-07-21.
 */

public interface UserService {
    @POST("/api/user")
    Call<User> signUpByEmail(@Body SignUpForm user);


    @POST("/oauth/token")
    @Headers({"Content-Type : application/x-www-form-urlencoded",
            "Authorization : Basic dGhhbmtzQ2xpZW50SWQ6dGhhbmtzU2VjcmV0"})
    Call<LoginResponseForm> login(@Body LoginForm user) ;
}
