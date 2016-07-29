package com.yellowfuture.thanku.model;

import lombok.Data;

/**
 * Created by zuby on 2016-07-27.
 */
@Data
public class RestaurantOrderMenu extends BaseModel {

    Long restaurantOrder;
    RestaurantMenu restaurantMenu;

    int count;

    int price;
}
