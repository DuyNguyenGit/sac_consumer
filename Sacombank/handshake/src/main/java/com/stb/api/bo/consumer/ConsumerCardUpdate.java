package com.stb.api.bo.consumer;

import com.stb.api.model.STBResponse;

/**
 * Created by nguyenletruong on 9/13/17.
 */

public class ConsumerCardUpdate extends STBResponse {
    public String DeviceID;
    public String LanguageID;
    public String RefNumber;
    public String CardNumber;
    public String CardToken;
    /*
    - True: Thẻ được mặc định
    - False: Thẻ không được mặc định
     */
    public Boolean DefaultIndicator;
    /*
    - True: Thẻ được liên kết
    - False: Thẻ không được liên kết
     */
    public Boolean LinkedIndicator;
}
