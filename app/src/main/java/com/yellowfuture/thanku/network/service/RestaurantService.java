package com.yellowfuture.thanku.network.service;

import com.yellowfuture.thanku.model.Category;
import com.yellowfuture.thanku.model.Restaurant;
import com.yellowfuture.thanku.model.RestaurantInfo;
import com.yellowfuture.thanku.model.RestaurantMenu;
import com.yellowfuture.thanku.model.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zuby on 2016. 7. 23..
 */
public interface RestaurantService {

    @GET("/api/restaurant/categories")
    Call<List<Category>> findCategoryAll(@Header("Authorization") String authorization);

    @GET("/api/restaurant/category/{category}")
    Call<List<Restaurant>> findByCategory(@Header("Authorization") String authorization,@Path("category") long id,@Query("lat") double lat, @Query("lon") double lon);

    @GET("/api/restaurant/{id}")
    Call<Restaurant> find(@Header("Authorization") String authorization,@Path("id") long id);

    @GET("/api/restaurant/{id}/menu")
    Call<List<RestaurantMenu>> findMenu(@Header("Authorization") String authorization,@Path("id") long id);

    @GET("/api/restaurant/{id}/review")
    Call<List<Review>> findReview(@Header("Authorization") String authorization,@Path("id") long id);

    @GET("/api/restaurant/{id}/info")
    Call<RestaurantInfo> findinfo(@Header("Authorization") String authorization,@Path("id") long id);
}
