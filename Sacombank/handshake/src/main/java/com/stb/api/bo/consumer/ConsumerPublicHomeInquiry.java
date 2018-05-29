package com.stb.api.bo.consumer;

import com.stb.api.model.STBResponse;
import com.stb.api.bo.consumer.L2.*;

/**
 * Created by DUY on 8/24/2017.
 */

public class ConsumerPublicHomeInquiry extends STBResponse {

    //request
    public String DeviceID;
    public String LanguageID;
    public String RefNumber;
    //response
    public FeatureObject[] FeatureObject;
    public PromotionObject[] PromotionObject;
    public SuggestionObject[] SuggestionObject;
    public CategoryObject[] CategoryObject;


}
