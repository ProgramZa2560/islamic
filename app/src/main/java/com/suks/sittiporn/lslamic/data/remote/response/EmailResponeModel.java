package com.suks.sittiporn.lslamic.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmailResponeModel {

    @SerializedName("result")
    private String success;
    @SerializedName("mesage")
    private String message;
    @SerializedName("data")
    private List<EmailDataResponseModel> data;

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

    public List<EmailDataResponseModel> getData() {
        return data;
    }

    public void setData(List<EmailDataResponseModel> data) {
        this.data = data;
    }
}
