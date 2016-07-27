package com.yellowfuture.thanku.model;

import lombok.Data;

/**
 * Created by zuby on 2016-07-27.
 */
@Data
public class RestaurantMenu extends BaseModel {

    private String name;
    private int price;
    private String url;
    private String comment;

}
