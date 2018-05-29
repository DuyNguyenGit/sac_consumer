package com.stb.api.bo.consumer;

import com.stb.api.model.STBResponse;

/**
 * Created by nguyenletruong on 8/30/17.
 */

public class ConsumerPasswordChange extends STBResponse {
    //request
    public String DeviceID;
    public String LanguageID;
    public String RefNumber;
    public String MobileNo;
    public String PasswordEncrypted;
    public String PKCS1Parameter;
    public String RN;
    public String OldPassword;
    public String NewPassword;
}
