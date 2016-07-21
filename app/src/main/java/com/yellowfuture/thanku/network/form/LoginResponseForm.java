package com.yellowfuture.thanku.network.form;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by zuby on 2016-07-21.
 */
@Data
public class LoginResponseForm {
    @SerializedName("access_token")
    @Expose
    String accessToken;
    @SerializedName("token_type")
    @Expose
    String tokenType;
    @SerializedName("refresh_token")
    @Expose
    String refreshToken;
}
