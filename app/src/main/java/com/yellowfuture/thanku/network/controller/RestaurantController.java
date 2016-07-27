package com.yellowfuture.thanku.network.controller;

import android.content.Context;

import com.yellowfuture.thanku.model.Category;
import com.yellowfuture.thanku.model.Restaurant;
import com.yellowfuture.thanku.network.RestApi;
import com.yellowfuture.thanku.network.service.RestaurantService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by zuby on 2016. 7. 23..
 */
public class RestaurantController extends BaseController {

    RestaurantService restaurantService;
    protected static BaseController instance;

    @Override
    protected void initService() {
        if(restaurantService == null)
            restaurantService = retrofit.create(RestaurantService.class);
    }

    public RestaurantController(Context context) {
        super(context);
    }


    public static synchronized RestaurantController getInstance(Context context) {
        if (instance == null) {
            instance = new RestaurantController(context);
        }
        return (RestaurantController) instance;
    }

    public void findCategoryAll(String accessToken, Callback<List<Category>> callback) {
        Call<List<Category>> call = restaurantService.findCategoryAll(RestApi.BEARER + accessToken);
        call.enqueue(callback);
    }

    public void findByCategory(String accessToken, long id,double lat, double lon, Callback<List<Restaurant>> callback) {
        Call<List<Restaurant>> call = restaurantService.findByCategory(RestApi.BEARER + accessToken, id, lat, lon);
        call.enqueue(callback);
    }

    public void find(String accessToken,long id, Callback<Restaurant> callback) {
        Call<Restaurant> call = restaurantService.find(RestApi.BEARER + accessToken,id);
        call.enqueue(callback);
    }
}
