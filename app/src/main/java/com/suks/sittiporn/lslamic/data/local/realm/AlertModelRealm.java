package com.suks.sittiporn.lslamic.data.local;

import io.realm.RealmObject;

public class AlertModelRealm extends RealmObject {

    private String id;
    private String date;
    private String switchDate;
    private String check;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSwitchDate() {
        return switchDate;
    }

    public void setSwitchDate(String switchDate) {
        this.switchDate = switchDate;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}
