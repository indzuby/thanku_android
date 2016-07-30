package com.yellowfuture.thanku.network.form;

import com.google.gson.annotations.Expose;
import com.yellowfuture.thanku.model.User;

import lombok.Data;

/**
 * Created by zuby on 2016-07-21.
 */
@Data
public class SignUpForm extends BaseForm{

    private User.SignUpType signUpType;


    private String phone;

    private String password;

    private String name;

    private String email;

    private String socialAccessToken;

    private String nickname;

    private String profilePath;

}
