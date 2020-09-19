package com.suks.sittiporn.lslamic.model.reponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExaminationSetListCreateModel {

    @SerializedName("success")
    private String success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<ExaminationSetListCreateDataReponseModel> examinationSetListCreateDataReponseModels;

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

    public List<ExaminationSetListCreateDataReponseModel> getExaminationSetListCreateDataReponseModels() {
        return examinationSetListCreateDataReponseModels;
    }

    public void setExaminationSetListCreateDataReponseModels(List<ExaminationSetListCreateDataReponseModel> examinationSetListCreateDataReponseModels) {
        this.examinationSetListCreateDataReponseModels = examinationSetListCreateDataReponseModels;
    }
}
