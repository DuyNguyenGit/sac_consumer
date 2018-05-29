package com.sacombank.consumers.models.staticmodels;

/**
 * Created by DUY on 8/29/2017.
 */

public class HomeFeatureModel {

    private int resId;
    private String text;

    public HomeFeatureModel(int resId, String text) {
        this.resId = resId;
        this.text = text;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
