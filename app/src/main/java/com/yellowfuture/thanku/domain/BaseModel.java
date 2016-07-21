package com.yellowfuture.thanku.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

/**
 * Created by zuby on 2016-07-21.
 */
@Data
public class BaseModel{

    @SerializedName("id")
    @Expose
    protected Long id;
    @SerializedName("createTime")
    @Expose
    protected Date createTime;
    @SerializedName("updatedTime")
    @Expose
    protected Date updatedTime;
}
