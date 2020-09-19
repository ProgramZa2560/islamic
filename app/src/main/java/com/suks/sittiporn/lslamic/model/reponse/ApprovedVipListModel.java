package com.suks.sittiporn.lslamic.model.reponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApprovedVipListModel {

    @SerializedName("result")
    private String result;
    @SerializedName("mesage")
    private String mesage;
    @SerializedName("data")
    private List<ApprovedVipListDataReponseModel> data;

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

    public List<ApprovedVipListDataReponseModel> getData() {
        return data;
    }

    public void setData(List<ApprovedVipListDataReponseModel> data) {
        this.data = data;
    }
}
