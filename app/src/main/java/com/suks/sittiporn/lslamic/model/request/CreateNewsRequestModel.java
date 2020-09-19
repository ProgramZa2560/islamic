package com.suks.sittiporn.lslamic.model.request;

public class CreateNewsRequestModel {

    private String rongphai_news_head;
    private String rongphai_news_descrition;
    private String img;
    private String nameImg;
    private String date;
    private String time;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
