package com.suks.sittiporn.lslamic.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExaminationListModel {

    @SerializedName("success")
    private String success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<ExaminationListDataReponseModel> examinationListDataReponseModels;

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

    public List<ExaminationListDataReponseModel> getExaminationListDataReponseModels() {
        return examinationListDataReponseModels;
    }

    public void setExaminationListDataReponseModels(List<ExaminationListDataReponseModel> examinationListDataReponseModels) {
        this.examinationListDataReponseModels = examinationListDataReponseModels;
    }
}
