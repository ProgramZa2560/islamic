package com.suks.sittiporn.lslamic.data.remote.request;

public class VipPaymentCreateRequestModel {

    private String vip_user_id;
    private String vip_status;
    private String img;
    private String imgName;
    private String date;


    public String getVip_user_id() {
        return vip_user_id;
    }

    public void setVip_user_id(String vip_user_id) {
        this.vip_user_id = vip_user_id;
    }

    public String getVip_status() {
        return vip_status;
    }

    public void setVip_status(String vip_status) {
        this.vip_status = vip_status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
