package com.yellowfuture.thanku.network.form;

import com.yellowfuture.thanku.model.OrderObject;
import com.yellowfuture.thanku.model.Restaurant;
import com.yellowfuture.thanku.model.RestaurantOrder;
import com.yellowfuture.thanku.model.RestaurantOrderMenu;
import com.yellowfuture.thanku.model.Review;
import com.yellowfuture.thanku.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * Created by zuby on 2016. 7. 24..
 */
@Data
public class OrderObjectForm extends BaseForm{

    Long id;
    // common

    public OrderObject.OrderType type;

    Review review;
    /**
     * 주문자 키
     */
    private User order;

    private int price;

    private int addPrice;

    private double lon;

    private double lat;

    private String address;

    private String comment;

    // buy
    private String orderTel;

    private String receiverTel;


    // errand

    private boolean matchYn;

    private Date matchDate;

    //quick
    private double startLat;
    private double startLon;
    private String startAddr;
    private double endLat;
    private double endLon;
    private String endAddr;
    private boolean reservYn;
    private Date reservDate;

    private String objectType;

    //Restaurant
    private Restaurant restaurant;

    private Long restaurantId;

    private List<RestaurantOrderMenu> menuList;

    public OrderObject toOrderObject(Class<? extends OrderObject> type) {
        return modelMapper.map(this,type);
    }
}

