package com.yellowfuture.thanku.model;

import lombok.Data;

/**
 * Created by zuby on 2016-07-29.
 */
@Data
public class RestaurantInfo extends BaseModel {

    String notification;

    String description;

    String businessHours;

    String address;
}
