package com.stb.api.bo.consumer;

import com.stb.api.model.STBResponse;

/**
 * Created by nguyenletruong on 9/13/17.
 */

public class ConsumerPromotionDetails extends STBResponse {
    //request
    public String DeviceID;
    public String LanguageID;
    public String RefNumber;
    public Integer PromotionID;
    //response
    public String Content;
    public String BannerLink;
    public Integer SuggestID;
    public String Rating;
    public Integer LikeCounter;
    public String StartDate;
    public String EndDate;
    public String Title;
    public String TitleHighLight;
}
