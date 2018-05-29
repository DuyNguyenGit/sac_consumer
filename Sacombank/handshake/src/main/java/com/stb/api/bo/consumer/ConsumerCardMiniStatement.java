package com.stb.api.bo.consumer;

import com.stb.api.model.STBResponse;
import com.stb.api.bo.consumer.L2.TransactionObject;
/**
 * Created by nguyenletruong on 9/7/17.
 */

public class ConsumerCardMiniStatement extends STBResponse {
    public String DeviceID;
    public String LanguageID;
    public String RefNumber;
//    public String FromDate;
//    public String ToDate;
    public String CardToken;
    //reponse
    public TransactionObject[] TransactionObject;
}
