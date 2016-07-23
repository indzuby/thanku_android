package com.yellowfuture.thanku.network.controller;

import android.content.Context;
import android.util.Base64;

import com.yellowfuture.thanku.model.User;
import com.yellowfuture.thanku.network.RestApi;
import com.yellowfuture.thanku.network.form.LoginForm;
import com.yellowfuture.thanku.network.form.LoginResponseForm;
import com.yellowfuture.thanku.network.form.SignUpForm;
import com.yellowfuture.thanku.network.service.UserService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by zuby on 2016-07-21.
 */
public class UserController extends BaseController {

    UserService userService;
    protected static BaseController instance ;

    public UserController(Context context) {
        super(context);
    }

    @Override
    protected void initService() {
        if(userService == null)
            userService = retrofit.create(UserService.class);
    }

    public static synchronized UserController getInstance(Context context) {
        if(instance ==null) {
            instance = new UserController(context);
        }
        return (UserController) instance;
    }
    public void signUpByEmail(SignUpForm user, Callback<User> callback){
        Call<User> call = userService.signUpByEmail(user);
        call.enqueue(callback);
    }
    public void login(LoginForm user, Callback<LoginResponseForm> callback) {
        Map<String ,String > fieldMap = new HashMap<>();
        fieldMap.put("scope",user.getScope());
        fieldMap.put("client_id",user.getClient_id());
        fieldMap.put("client_secret",user.getClient_secret());
        fieldMap.put("grant_type",user.getGrant_type());
        fieldMap.put("password",user.getPassword());
        fieldMap.put("username",user.getUsername());

        String authorization = "Basic "+ Base64.encodeToString((RestApi.CLIENT_ID+":"+RestApi.CLIENT_SECRET).getBytes(),Base64.NO_WRAP);

        Call<LoginResponseForm> call = userService.login(authorization,fieldMap);
        call.enqueue(callback);
    }

    public void myInfo(String accessToken,Callback<User> callback){
        Call<User> call = userService.myInfo(RestApi.BEARER + accessToken);
        call.enqueue(callback);
    }

    public void update(String accessToken,User user,Callback<User> callback){
        Call<User> call = userService.update(RestApi.BEARER + accessToken,user);
        call.enqueue(callback);
    }
}
