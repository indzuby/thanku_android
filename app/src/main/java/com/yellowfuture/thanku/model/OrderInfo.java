package com.yellowfuture.thanku.model;

import com.yellowfuture.thanku.network.form.OrderObjectForm;

import java.util.List;

import lombok.Data;

/**
 * Created by zuby on 2016. 7. 24..
 */
@Data
public class OrderInfo extends BaseModel{
    private long price;

    private String comment;

    private String orderDate;

    private int count;

    private User order;

    private List<OrderObject> items;
    private List<List<OrderObjectForm>> groupItems;
}
