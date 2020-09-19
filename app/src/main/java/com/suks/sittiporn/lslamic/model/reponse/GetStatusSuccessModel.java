package com.suks.sittiporn.lslamic.model.reponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetStatusSuccessModel {

    @SerializedName("result")
    private String result;
    @SerializedName("mesage")
    private String mesage;
    @SerializedName("data")
    private List<GetStatusDataReponseModel> getStatusDataReponseModel;

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

    public List<GetStatusDataReponseModel> getGetStatusDataReponseModel() {
        return getStatusDataReponseModel;
    }

    public void setGetStatusDataReponseModel(List<GetStatusDataReponseModel> getStatusDataReponseModel) {
        this.getStatusDataReponseModel = getStatusDataReponseModel;
    }
}

