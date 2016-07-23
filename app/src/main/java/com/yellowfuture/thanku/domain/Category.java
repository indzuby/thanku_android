package com.yellowfuture.thanku.domain;

import lombok.Data;

/**
 * Created by zuby on 2016. 7. 23..
 */
@Data
public class Category extends BaseModel {

    //한식&중식 일&돈까스&회 치킨&피자 보쌈&족발 야식 탕&찜 도시락 프렌차이즈 카페&디저트 편의점
    public enum CategoryType{
        KOFOOD,JPFOOD,CHICKEN,PIGFOOD,NIGHTFOOD,SOUPFOOD,LUNCHFOOD,FRANCHISE,CAFE,CONVENIENCE
    }

    private String name;

    private int priority;

    private CategoryType type;


}
