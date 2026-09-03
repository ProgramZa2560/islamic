package com.suks.sittiporn.lslamic.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class MemberModel {

//    @SerializedName("result")
//    @SerializedName("mesage")
//    @SerializedName("data")

    @SerializedName("result")
    private String result;
    @SerializedName("mesage")
    private String mesage;
    @SerializedName("data")
    private MemberDataReponseModel data;

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

    public MemberDataReponseModel getData() {
        return data;
    }

    public void setData(MemberDataReponseModel data) {
        this.data = data;
    }
}
