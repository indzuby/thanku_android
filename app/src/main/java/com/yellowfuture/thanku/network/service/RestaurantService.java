package com.yellowfuture.thanku.network.service;

import com.yellowfuture.thanku.domain.Category;
import com.yellowfuture.thanku.domain.Restaurant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by zuby on 2016. 7. 23..
 */
public interface RestaurantService {

    @GET("/api/restaurant/categories")
    Call<List<Category>> findCategoryAll(@Header("Authorization") String authorization);

    @GET("/api/restaurant/category/{category}")
    Call<List<Restaurant>> findByCategory(@Header("Authorization") String authorization,@Path("category") long id);
}
