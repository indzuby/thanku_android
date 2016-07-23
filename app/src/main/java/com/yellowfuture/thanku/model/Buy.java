package com.yellowfuture.thanku.model;

import lombok.Data;

/**
 * Created by zuby on 2016. 7. 24..
 */
@Data
public class Buy extends OrderObject {

    private String orderTel;

    private String receiverTel;

    private String address;

    private double lat;
    private double lon;
}
