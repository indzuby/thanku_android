package com.yellowfuture.thanku.network.form;

import com.google.gson.annotations.Expose;

import lombok.Data;

/**
 * Created by zuby on 2016-07-21.
 */
@Data
public class SignUpForm extends BaseForm{
    private String phone;

    private String password;

    private String name;

    private String email;

}
