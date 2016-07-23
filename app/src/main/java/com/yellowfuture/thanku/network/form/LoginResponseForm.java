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
    String accessToken;

    @SerializedName("token_type")
    String tokenType;

    @SerializedName("refresh_token")
    String refreshToken;
}
