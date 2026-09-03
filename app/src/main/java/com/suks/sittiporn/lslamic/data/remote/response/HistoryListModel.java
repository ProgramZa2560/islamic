package com.suks.sittiporn.lslamic.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryListModel {

    @SerializedName("success")
    private String success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<HistoryListDataReponseModel> historyListDataReponseModels;

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

    public List<HistoryListDataReponseModel> getHistoryListDataReponseModels() {
        return historyListDataReponseModels;
    }

    public void setHistoryListDataReponseModels(List<HistoryListDataReponseModel> historyListDataReponseModels) {
        this.historyListDataReponseModels = historyListDataReponseModels;
    }
}
