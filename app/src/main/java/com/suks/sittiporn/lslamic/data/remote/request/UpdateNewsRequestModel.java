package com.suks.sittiporn.lslamic.data.remote.request;

public class UpdateNewsRequestModel {

    private int rongphai_news_id;
    private String rongphai_news_head;
    private String rongphai_news_descrition;
    private String img;
    private String nameImg;

    public int getRongphai_news_id() {
        return rongphai_news_id;
    }

    public void setRongphai_news_id(int rongphai_news_id) {
        this.rongphai_news_id = rongphai_news_id;
    }

    public String getRongphai_news_head() {
        return rongphai_news_head;
    }

    public void setRongphai_news_head(String rongphai_news_head) {
        this.rongphai_news_head = rongphai_news_head;
    }

    public String getRongphai_news_descrition() {
        return rongphai_news_descrition;
    }

    public void setRongphai_news_descrition(String rongphai_news_descrition) {
        this.rongphai_news_descrition = rongphai_news_descrition;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNameImg() {
        return nameImg;
    }

    public void setNameImg(String nameImg) {
        this.nameImg = nameImg;
    }
}
