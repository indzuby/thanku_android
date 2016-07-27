package com.yellowfuture.thanku.model;

import com.yellowfuture.thanku.network.form.OrderObjectForm;

import java.util.List;

import lombok.Data;

/**
 * Created by zuby on 2016. 7. 24..
 */
@Data
public class OrderInfo extends BaseModel{

    public enum OrderState {
        PENDING,MATCH,COMPLETE;
    }


    private long price;

    private long deliveryPrice;


    private String comment;

    private String orderDate;

    private int count;

    private User order;

    private OrderState state = OrderState.PENDING;


    private List<OrderObjectForm> items;

    public long getBasicPrice(){
        return price - deliveryPrice;
    }
}
