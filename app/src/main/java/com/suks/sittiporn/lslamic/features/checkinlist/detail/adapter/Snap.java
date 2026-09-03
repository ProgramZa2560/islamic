package com.suks.sittiporn.lslamic.features.checkinlist.detail.adapter;


import android.content.Context;

import com.suks.sittiporn.lslamic.data.remote.response.ImgReponseModel;

import java.util.List;

public class Snap {

    private int mGravity;
    private String mText;
    private List<ImgReponseModel> mApps;
    Context context;

    public Snap(int gravity, String text, List<ImgReponseModel> apps, Context context) {
        mGravity = gravity;
        mText = text;
        mApps = apps;
        this.context = context;
    }

    public String getText(){
        return mText;
    }

    public int getGravity(){
        return mGravity;
    }


    public List<ImgReponseModel> getApps(){
        return mApps;
    }

}
