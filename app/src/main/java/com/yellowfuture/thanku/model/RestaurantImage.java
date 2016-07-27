package com.yellowfuture.thanku.model;

import lombok.Data;

/**
 * Created by zuby on 2016-07-27.
 */
@Data
public class RestaurantImage extends BaseModel {

    private String url;

    @Override
    public String toString() {
        return url;
    }
}
