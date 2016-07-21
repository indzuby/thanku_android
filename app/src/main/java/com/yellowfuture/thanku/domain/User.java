package com.yellowfuture.thanku.domain;

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

    private String phone;

    private String password;

    private String name;

    private String email;

    private String socialId;

    private String socialAccessToken;

    private String profilePath = "/profile/default.png";

    private SignUpType signUpType;

}
