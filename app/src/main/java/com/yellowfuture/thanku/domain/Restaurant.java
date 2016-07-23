package com.yellowfuture.thanku.domain;

import lombok.Data;

/**
 * Created by zuby on 2016. 7. 23..
 */
@Data
public class Restaurant extends BaseModel {

    String name;

    String businessHours;

    String url;

    int likeCount;

    int commentCount;

    int callCount;
}
