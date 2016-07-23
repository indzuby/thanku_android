package com.yellowfuture.thanku.model;

import java.util.Date;

import lombok.Data;

/**
 * Created by zuby on 2016. 7. 24..
 */
@Data
public class OrderObject extends BaseModel {
    public enum OrderType {
        /**
         * 퀵
         */
        QUICK("Q", Quick.class)
        /**
         * 생활편의
         */
        , ERRAND("E", Errand.class)
        /**
         * 맛집
         */
        , RESTAURANT("R", RestaurantOrder.class)
        /**
         * 사다주기
         */
        , BUY("B", Buy.class);

        public final String value;
        public final Class<? extends OrderObject> type;
        OrderType(String value, Class<? extends OrderObject> type) {
            this.value = value;
            this.type = type;
        }

        @Override
        public String toString() {
            return this.name();
        }
    }

    private OrderType type;

    private boolean orderYn;

    private User order;

    private User rider;


    private int price;

    private int addPrice;


    private boolean matchYn;


    private Date matchDate;

    private String objectType;

    private String comment;
}
