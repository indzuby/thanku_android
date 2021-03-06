package com.yellowfuture.thanku.network.form;

import com.yellowfuture.thanku.network.RestApi;

import lombok.Data;

/**
 * Created by zuby on 2016-07-21.
 */
@Data
public class LoginForm extends BaseForm{

    private String scope ="read write delete";
    private String client_secret = RestApi.CLIENT_SECRET;
    private String client_id = RestApi.CLIENT_ID;
    private String username;
    private String password;
    private String grant_type = RestApi.GRANT_TYPE;

    public LoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
