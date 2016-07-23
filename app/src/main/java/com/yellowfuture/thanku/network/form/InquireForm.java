package com.yellowfuture.thanku.network.form;

import com.yellowfuture.thanku.model.BaseModel;

import lombok.Data;

/**
 * Created by zuby on 2016. 7. 23..
 */
@Data
public class InquireForm extends BaseModel {

    String phone;

    String email;

    String content;
}
