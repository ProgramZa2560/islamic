package com.suks.sittiporn.lslamic.data.remote.response;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@Parcel
public class LocationListModel implements Serializable {
    @SerializedName("success")
    private String success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<LocationReponseModel> data;

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

    public List<LocationReponseModel> getData() {
        return data;
    }

    public void setData(List<LocationReponseModel> data) {
        this.data = data;
    }
}
