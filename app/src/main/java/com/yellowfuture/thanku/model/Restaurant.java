package com.yellowfuture.thanku.model;

import java.util.List;

import lombok.Data;

/**
 * Created by zuby on 2016. 7. 23..
 */
@Data
public class Restaurant extends BaseModel {

    String name;

    String businessHours;

    String url;

    int likeCount;

    int commentCount;

    int callCount;

    List<RestaurantMenu> menuList;

    List<RestaurantImage> imageList;

    List<Review> reviewList;

    double lat;

    double lon;

    double avgScore;
}
