package com.sacombank.consumers.models.home;

/**
 * Created by DUY on 7/14/2017.
 */

public class CategoryReport {
    private String title = "Chương trình Khuyến mãi";
    private String desc = "Mô tả ngắn, giới thiệu bài viết khuyến mãi. Mô tả ngắn, giới thiệu bài viết khuyến mãi. Mô tả ngắn, giới thiệu bài viết khuyến mãi. ";
    private String imageUrl = "https://www.sacombank.com.vn/the/pictures/logo.png";

    public CategoryReport() {
    }

    public CategoryReport(String title, String desc, String imageUrl) {
        this.title = title;
        this.desc = desc;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
