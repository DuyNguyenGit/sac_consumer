package com.stb.api.bo.consumer;

import com.stb.api.model.STBResponse;

/**
 * Created by nguyenletruong on 8/29/17.
 */

public class ConsumerPasswordCreation extends STBResponse {
    //request
    public String DeviceID;
    public String LanguageID;
    public String RefNumber;
    public String Platform;
    public String OSVersion;
    public String Model;
    public String MobileNo;
    public String PasswordEncrypted;
    public String PKCS1Parameter;
    public String RN;
    public String Password;
}
