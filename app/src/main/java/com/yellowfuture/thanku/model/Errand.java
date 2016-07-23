package com.yellowfuture.thanku.model;

import java.util.Date;

import lombok.Data;

/**
 * Created by zuby on 2016. 6. 15..
 */
@Data
public class Errand extends OrderObject{


    private boolean reservYn;

    private boolean matchYn;

    private double lat;

    private double lon;

    private String address;

    private Date reservDate;

    private Date matchDate;
}
