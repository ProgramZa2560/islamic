package com.suks.sittiporn.lslamic.data.remote.request;

public class CreateExaminationHeadRequestModel {

    private String examination_head_connection_id;
    private String examination_head_name;
    private String examination_head_type;

    public String getExamination_head_connection_id() {
        return examination_head_connection_id;
    }

    public void setExamination_head_connection_id(String examination_head_connection_id) {
        this.examination_head_connection_id = examination_head_connection_id;
    }

    public String getExamination_head_name() {
        return examination_head_name;
    }

    public void setExamination_head_name(String examination_head_name) {
        this.examination_head_name = examination_head_name;
    }

    public String getExamination_head_type() {
        return examination_head_type;
    }

    public void setExamination_head_type(String examination_head_type) {
        this.examination_head_type = examination_head_type;
    }
}
