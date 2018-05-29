package com.stb.api.bo.consumer;

import com.stb.api.model.STBResponse;

/**
 * Created by nguyenletruong on 9/13/17.
 */

public class ConsumerCardBalanceInquiry extends STBResponse {
    public String DeviceID;
    public String LanguageID;
    public String RefNumber;
    public String CardNumber;
    public String CardToken;
    //response
    public String AvailableBalance;
    public String CurrencyCode;
}
