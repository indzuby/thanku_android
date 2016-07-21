package com.yellowfuture.thanku.network;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zuby on 2016. 7. 20..
 */
public class RestApi {

    public static final String CLIENT_ID = "thanksClientId";
    public static final String CLIENT_SECRET = "thanksSecret";
    public static final String PASSWORD = "password";
    public static final String REFRESH_TOKEN = "refresh_token";
    private static final String RESOURCE_ID = "thanksRestId";

    public final static String url = "http://192.168.0.8:3000/";

    Context mContext;
    private static RestApi instance ;
    Retrofit retrofit;
    public RestApi(Context mContext) {
        this.mContext = mContext;
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RestApi getInstance(Context context) {
        if(instance ==null) {
            instance = new RestApi(context);
        }
        return instance;
    }

}
