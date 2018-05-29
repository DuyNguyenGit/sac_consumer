package com.stb.api.bo.consumer.L2;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by DUY on 8/24/2017.
 */

public class SuggestionObject implements Serializable {

    public Integer SuggestionOrder;
    public Integer LikeCounter;
    public Integer CategoryID;
    public String SuggestionImage;
    public String SuggestionLink;
    public String SuggestionSummary;
    public String SuggestID;
    public String Rating;
    public String StartDate;
    public String EndDate;
    public String Title;
    public String TitleHighLight;
    public Integer[] CategoryIDList;

}
