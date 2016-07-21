package com.yellowfuture.thanku.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

/**
 * Created by zuby on 2016-07-21.
 */
@Data
public class Advertisement extends BaseModel {
    public enum AdvertisementType{
        START,BANNER,POPUP,NATIVE
    }

    private String url;

    private AdvertisementType type;

    private Date startTime;

    private Date endTime;

    private int priority;
}
