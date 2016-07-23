package com.yellowfuture.thanku.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yellowfuture.thanku.utils.ImproveDateTypeAdapter;

import java.util.Date;

import lombok.Getter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zuby on 2016. 7. 20..
 */
public class RestApi {

    public static final String CLIENT_ID = "thanksClientId";
    public static final String CLIENT_SECRET = "thanksSecret";
    public static final String GRANT_TYPE = "password";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String BEARER = "Bearer ";
    private static final String RESOURCE_ID = "thanksRestId";

    public final static String url = "http://192.168.0.19:8080";

    @Getter
    private Context context;
    private static RestApi instance ;
    @Getter
    private Retrofit retrofit;


    public RestApi(Context mContext) {
        this.context = mContext;
        GsonBuilder builder = new GsonBuilder();

// Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new ImproveDateTypeAdapter());

        Gson gson = builder.create();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized RestApi getInstance(Context context) {
        if(instance ==null) {
            instance = new RestApi(context);
        }
        return instance;
    }


}
