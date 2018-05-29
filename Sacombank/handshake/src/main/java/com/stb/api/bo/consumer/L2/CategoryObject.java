package com.stb.api.bo.consumer.L2;

/**
 * Created by DUY on 8/24/2017.
 */

public class CategoryObject implements Comparable<CategoryObject>  {

    public Integer CategoryID;
    public Integer Order;
    public Integer ActiveIconID;
    public String Name;
    public String ActiveIconUrl;
    public Integer InactiveIconID;
    public String InactiveIconUrl;
    public String LanguageID;

    @Override
    public int compareTo(CategoryObject object) {
        int temp = -1;
        if (this.Order > object.Order)
            temp = 1;
        else if (this.Order < object.Order)
            temp = -1;
        return temp;
    }
}
