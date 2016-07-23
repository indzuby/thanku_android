package com.yellowfuture.thanku.model;

import lombok.Data;

/**
 * Created by zuby on 2016. 7. 24..
 */
@Data
public class RestaurantOrder extends OrderObject {


    Restaurant restaurant;

    int price;
}
