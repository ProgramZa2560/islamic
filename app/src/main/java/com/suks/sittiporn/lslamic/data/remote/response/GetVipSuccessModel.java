package com.suks.sittiporn.lslamic.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetVipSuccessModel {

    @SerializedName("result")
    private String result;
    @SerializedName("mesage")
    private String mesage;
    @SerializedName("data")
    private List<GetVipDataReponseModel> data;

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

    public List<GetVipDataReponseModel> getData() {
        return data;
    }

    public void setData(List<GetVipDataReponseModel> data) {
        this.data = data;
    }
}

