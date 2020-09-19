package com.suks.sittiporn.lslamic.model.request;

public class UpdateStatusPaymentRequestModel {

    private int examination_status_id;
    private int examination_status_status;
    private String url;
    private String nameUrl;

    public int getExamination_status_id() {
        return examination_status_id;
    }

    public void setExamination_status_id(int examination_status_id) {
        this.examination_status_id = examination_status_id;
    }

    public int getExamination_status_status() {
        return examination_status_status;
    }

    public void setExamination_status_status(int examination_status_status) {
        this.examination_status_status = examination_status_status;
    }

    public String getNameUrl() {
        return nameUrl;
    }

    public void setNameUrl(String nameUrl) {
        this.nameUrl = nameUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
