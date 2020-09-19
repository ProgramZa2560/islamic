package com.suks.sittiporn.lslamic.model.reponse;

import com.google.gson.annotations.SerializedName;

public class SuccessModel {

    @SerializedName("result")
    private String result;
    @SerializedName("mesage")
    private String mesage;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMesage() {
        return mesage;
    }

    public void setMesage(String mesage) {
        this.mesage = mesage;
    }
}

