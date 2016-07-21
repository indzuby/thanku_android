package com.yellowfuture.thanku.network.controller;

import android.content.Context;

import com.yellowfuture.thanku.domain.User;
import com.yellowfuture.thanku.network.form.LoginForm;
import com.yellowfuture.thanku.network.form.LoginResponseForm;
import com.yellowfuture.thanku.network.form.SignUpForm;
import com.yellowfuture.thanku.network.service.UserService;

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
        Call<LoginResponseForm> call = userService.login(user);
        call.enqueue(callback);
    }
}
