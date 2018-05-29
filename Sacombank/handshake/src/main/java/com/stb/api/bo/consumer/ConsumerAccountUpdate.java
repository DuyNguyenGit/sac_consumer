package com.stb.api.bo.consumer;

import com.stb.api.bo.consumer.L2.AccountObject;
import com.stb.api.model.STBResponse;

/**
 * Created by nguyenletruong on 9/13/17.
 */

public class ConsumerAccountUpdate extends STBResponse {
    //request
    public String DeviceID;
    public String LanguageID;
    public String RefNumber;
    public String MobileNo;
    public AccountObject AccountObject;
}
