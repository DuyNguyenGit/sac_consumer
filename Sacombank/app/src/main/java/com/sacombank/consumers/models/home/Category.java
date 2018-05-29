package com.sacombank.consumers.models.home;

/**
 * Created by DUY on 7/14/2017.
 */

public class Category {
    private String title = "Sacombank Category";
    private String imageUrl = "https://chovaytieudungnhanh.net/wp-content/uploads/2016/05/sacombank-logo-1-1.png";

    public Category() {
    }

    public Category(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
