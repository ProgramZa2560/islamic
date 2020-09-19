package com.suks.sittiporn.lslamic.model.request;

public class CreateHstoryRequestModel {

    private String user_id;
    private String set_id;
    private String history_score;
    private String history_total_exam;
    private String history_date;
    private String history_time;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSet_id() {
        return set_id;
    }

    public void setSet_id(String set_id) {
        this.set_id = set_id;
    }

    public String getHistory_score() {
        return history_score;
    }

    public void setHistory_score(String history_score) {
        this.history_score = history_score;
    }

    public String getHistory_total_exam() {
        return history_total_exam;
    }

    public void setHistory_total_exam(String history_total_exam) {
        this.history_total_exam = history_total_exam;
    }

    public String getHistory_date() {
        return history_date;
    }

    public void setHistory_date(String history_date) {
        this.history_date = history_date;
    }

    public String getHistory_time() {
        return history_time;
    }

    public void setHistory_time(String history_time) {
        this.history_time = history_time;
    }
}


