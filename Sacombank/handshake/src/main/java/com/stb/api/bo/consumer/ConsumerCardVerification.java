package com.stb.api.bo.consumer;

import com.stb.api.model.STBResponse;

/**
 * Created by nguyenletruong on 8/29/17.
 */

public class ConsumerCardVerification extends STBResponse {
    //request
    public String DeviceID;
    public String LanguageID;
    public String RefNumber;
    public String MobileNo;
    public String CardNumber;
    public String CardToken;
    public String ExpiryDate;
    public String CVV;
    //response
    public String FullName;
    public String AuthType;
    public Integer AuthLength;
}
