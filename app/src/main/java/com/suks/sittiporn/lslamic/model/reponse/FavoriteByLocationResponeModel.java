package com.suks.sittiporn.lslamic.model.reponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteByLocationResponeModel {

    @SerializedName("result")
    private String success;
    @SerializedName("mesage")
    private String message;
    @SerializedName("data")
    private List<FavoriteListDataResponseModel> data;

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

    public List<FavoriteListDataResponseModel> getData() {
        return data;
    }

    public void setData(List<FavoriteListDataResponseModel> data) {
        this.data = data;
    }
}
