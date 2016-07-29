package com.yellowfuture.thanku.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;

/**
 * Created by zuby on 2016. 7. 24..
 */
@Data
public class RestaurantOrder extends OrderObject {


    Restaurant restaurant;

    int price;

    private List<RestaurantOrderMenu> menuList = new ArrayList<>();
}
