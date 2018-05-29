package com.stb.api.bo.consumer;

import com.stb.api.model.STBResponse;

/**
 * Created by nguyenletruong on 9/15/17.
 */

public class ConsumerStatementInquiry extends STBResponse {
    public String DeviceID;
    public String LanguageID;
    public String RefNumber;
    public String CardToken;
    public String BillId;
    //response
    public String BillDetails;
}
