package com.suks.sittiporn.lslamic.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VipResponeModel {

    @SerializedName("result")
    private String success;
    @SerializedName("mesage")
    private String message;
    @SerializedName("data")
    private List<VipListDataResponseModel> vipListDataResponseModels;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<VipListDataResponseModel> getVipListDataResponseModels() {
        return vipListDataResponseModels;
    }

    public void setVipListDataResponseModels(List<VipListDataResponseModel> vipListDataResponseModels) {
        this.vipListDataResponseModels = vipListDataResponseModels;
    }
}
