package com.stb.api.bo.consumer;

import com.stb.api.model.STBResponse;

/**
 * Created by nguyenletruong on 8/29/17.
 */

public class ConsumerAuthCodeVerification extends STBResponse {
    //request
    public String DeviceID;
    public String LanguageID;
    public String RefNumber;
    public String MobileNo;
    public String CardNumber;
    public String CardToken;
    public String AuthType;
    public String AuthCode;
    public String PINEncrypted;
    public String PKCS1Parameter;
    public String RN;
}
