package com.suks.sittiporn.lslamic.model.reponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddCommentModel {

    @SerializedName("success")
    private String success;
    @SerializedName("message")
    private String message;

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

}
