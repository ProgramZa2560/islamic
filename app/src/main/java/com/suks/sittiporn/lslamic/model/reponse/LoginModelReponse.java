package com.suks.sittiporn.lslamic.model.reponse;

import com.google.gson.annotations.SerializedName;

public class LoginModelReponse {

    @SerializedName("success")
    private String success;
    @SerializedName("message")
    private String message;
    @SerializedName("loginView")
    private LiginDataReponseModel liginDataReponseModels;

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

    public LiginDataReponseModel getLiginDataReponseModels() {
        return liginDataReponseModels;
    }

    public void setLiginDataReponseModels(LiginDataReponseModel liginDataReponseModels) {
        this.liginDataReponseModels = liginDataReponseModels;
    }
}
