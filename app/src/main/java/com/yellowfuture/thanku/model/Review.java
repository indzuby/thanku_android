package com.yellowfuture.thanku.model;

import lombok.Data;

/**
 * Created by zuby on 2016-07-27.
 */
@Data
public class Review extends BaseModel {
    String comment="";
    User writer;
    int score =0;
    long orderObjectId;
}
