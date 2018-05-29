package com.stb.api.bo.consumer;

import com.stb.api.bo.consumer.L2.*;
import com.stb.api.model.STBResponse;

/**
 * Created by DUY on 8/27/2017.
 */

public class ConsumerAccountLogin extends STBResponse {
    //request
    public String DeviceID;
    public String LanguageID;
    public String RefNumber;
    public String MobileNo;
    public String Password;
    public String PasswordEncrypted;
    public String PKCS1Parameter;
    public String RN;
    public String FirebaseToken;
    //response
    public AccountObject AccountObject;
    public CardObject[] CardObject;
    public SuggestionObject[] SuggestionObject;

}
