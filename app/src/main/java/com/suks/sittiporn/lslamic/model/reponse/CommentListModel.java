package com.suks.sittiporn.lslamic.model.reponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentListModel {

    @SerializedName("success")
    private String success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<CommentReponseModel> data;

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

    public List<CommentReponseModel> getData() {
        return data;
    }

    public void setData(List<CommentReponseModel> data) {
        this.data = data;
    }
}
