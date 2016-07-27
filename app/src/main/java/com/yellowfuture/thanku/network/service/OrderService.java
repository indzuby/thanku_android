package com.yellowfuture.thanku.network.service;

import com.yellowfuture.thanku.model.OrderInfo;
import com.yellowfuture.thanku.model.OrderObject;
import com.yellowfuture.thanku.model.Review;
import com.yellowfuture.thanku.network.form.OrderObjectForm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by zuby on 2016. 7. 24..
 */
public interface OrderService {
    @GET("/api/order/{id}")
    Call<OrderInfo> getOrderInfo(@Header("Authorization") String authorization, @Path("id") Long id);


    @POST("/api/order")
    Call<OrderObject> addOrder(@Header("Authorization") String authorization, @Body OrderObjectForm orderObjectForm);

    @PUT("/api/order/{id}")
    Call<OrderObject> updateOrder(@Header("Authorization") String authorization, @Path("id") Long id, @Body OrderObjectForm orderObjectForm);

    @DELETE("/api/order/{id}")
    Call<Void> remove(@Header("Authorization") String authorization, @Path("id") Long id);

    @PUT("/api/order/ordering")
    Call<Void> ordering(@Header("Authorization") String authorization);

    @POST("/api/order/review")
    Call<Review> addReview(@Header("Authorization") String authorization, @Body Review reviewForm);

    @PUT("/api/order/review")
    Call<Review> editReview(@Header("Authorization") String authorization, @Body Review reviewForm);

}
