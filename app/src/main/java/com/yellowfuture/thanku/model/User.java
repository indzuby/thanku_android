package com.yellowfuture.thanku.model;

import com.google.gson.annotations.Expose;

import lombok.Data;

/**
 * Created by zuby on 2016-07-21.
 */
@Data
public class User extends BaseModel {
    public enum SignUpType{
        EMAIL,FACEBOOK,KAKAO
    }
    public enum SexType{
        MALE,FEMALE
    }
    private String phone;

    private String password;

    private String name;

    private String nickname;

    private String email;

    private String socialId;

    private String socialAccessToken;


    private String profilePath = "/profile/default.png";

    private SignUpType signUpType;

    private int point = 0;

    private SexType sexType;

    private Long birthday;

    private String address;

    private boolean smsReceiveYn;

    private boolean emailReceiveYn;

    private boolean pushReceiveYn;

}
