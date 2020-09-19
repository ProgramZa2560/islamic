package com.suks.sittiporn.lslamic.model.reponse;

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
    private MemberDataReponseModel memberDataReponseModel;

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

    public MemberDataReponseModel getMemberDataReponseModel() {
        return memberDataReponseModel;
    }

    public void setMemberDataReponseModel(MemberDataReponseModel memberDataReponseModel) {
        this.memberDataReponseModel = memberDataReponseModel;
    }
}
