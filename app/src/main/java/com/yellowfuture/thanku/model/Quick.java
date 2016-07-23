package com.yellowfuture.thanku.model;

import java.util.Date;

import lombok.Data;

/**
 * Created by zuby on 2016. 7. 24..
 */
@Data
public class Quick extends OrderObject {


    private double startLat;

    private double startLon;
    private String startAddr;

    // 배달 대상 위치
    private double endLat;
    private double endLon;
    private String endAddr;


    private boolean reservYn;


    private Date reservDate;
}

