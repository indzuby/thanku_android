package com.yellowfuture.thanku.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

/**
 * Created by zuby on 2016-07-21.
 */
@Data
public class BaseModel{


    protected Long id;

    protected Long createTime;

    public Date getCreateTime(){
        return new Date(createTime);
    }

    protected Long updatedTime;
    public Date getUpdatedTime(){
        return new Date(updatedTime);
    }
}
