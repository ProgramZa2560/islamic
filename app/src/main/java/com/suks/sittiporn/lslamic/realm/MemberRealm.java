package com.suks.sittiporn.lslamic.realm;

import io.realm.RealmObject;

public class MemberRealm extends RealmObject {

    String id, member_password, member_fistname, member_lastname, member_email,member_tel;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getMember_password() {
        return member_password;
    }
    public void setMember_password(String member_password) {
        this.member_password = member_password;
    }

    public String getMember_fistname() {
        return member_fistname;
    }
    public void setMember_fistname(String member_fistname) {
        this.member_fistname = member_fistname;
    }

    public String getMember_lastname() {
        return member_lastname;
    }
    public void setMember_lastname(String member_lastname) {
        this.member_lastname = member_lastname;
    }

    public String getMember_email() {
        return member_email;
    }
    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public String getMember_tel() {
        return member_tel;
    }
    public void setMember_tel(String member_tel) {
        this.member_tel = member_tel;
    }


}
