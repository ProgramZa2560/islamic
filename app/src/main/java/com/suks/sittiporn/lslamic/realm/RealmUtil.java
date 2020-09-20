package com.suks.sittiporn.lslamic.realm;

import android.content.Context;

import com.suks.sittiporn.lslamic.model.reponse.MemberModel;
import com.suks.sittiporn.lslamic.model.reponse.VipListDataResponseModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmUtil {

    static Realm realm;
    static boolean member = false;
    //    static Context context;
    static int count;
    static boolean update = false;

    private static void config(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
    }

    public static Boolean member() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        if (realm.where(LiginDataModelRealm.class).findAll() == null) {
            return false;
        } else if (realm.where(LiginDataModelRealm.class) == null) {
            return false;
        }
        RealmResults<LiginDataModelRealm> member_result = realm.where(LiginDataModelRealm.class).findAll();
        if (member_result.size() == 0)
            member = false;
        else
            member = true;
        realm.commitTransaction();
        return member;


    }

    public static int deleteRealm() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        if (realm.where(LiginDataModelRealm.class).findAll() == null) {
            return 0;
        } else if (realm.where(LiginDataModelRealm.class) == null) {
            return 0;
        }
        RealmResults<LiginDataModelRealm> Members = realm.where(LiginDataModelRealm.class).findAll();
        Members.deleteAllFromRealm();
        realm.commitTransaction();

        return Members.size();

    }

    public static String getMemberRealm() {
        ;
        String user_id = "";
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        if (realm.where(LiginDataModelRealm.class).findAll() == null) {
            return user_id;
        } else if (realm.where(LiginDataModelRealm.class) == null) {
            return user_id;
        }
        RealmResults<LiginDataModelRealm> realmMembers = realm.where(LiginDataModelRealm.class).findAll();
        realm.commitTransaction();
        if (realmMembers.size() == 0)
            return user_id;
        user_id = realmMembers.get(0).getUser_id();
        return user_id;

    }

    public static String getUserRealm() {
//        config(context);
//        realm.beginTransaction();
        String user = "";
//        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        if (realm.where(LiginDataModelRealm.class).findAll() == null) {
            return user;
        } else if (realm.where(LiginDataModelRealm.class) == null) {
            return user;
        } else if (realm.where(LiginDataModelRealm.class).findFirst() == null) {
            return user;
        } else if (realm.where(LiginDataModelRealm.class).findAll().size() == 0) {
            return user;
        }
        RealmResults<LiginDataModelRealm> realmMembers = realm.where(LiginDataModelRealm.class).findAll();
        user = realmMembers.get(0).getUser_user();
//        realm.commitTransaction();

        return user;
    }

    public static String getUserVipStatus() {
//        config(context);
//        realm.beginTransaction();
        String vipStatus = "";
//        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        if (realm.where(LiginDataModelRealm.class).findAll() == null) {
            return vipStatus;
        } else if (realm.where(LiginDataModelRealm.class) == null) {
            return vipStatus;
        } else if (realm.where(LiginDataModelRealm.class).findFirst() == null) {
            return vipStatus;
        } else if (realm.where(LiginDataModelRealm.class).findAll().size() == 0) {
            return vipStatus;
        }else  if (realm.where(LiginDataModelRealm.class).findFirst().getVip_status() == null) {
            return vipStatus;
        } else if (realm.where(LiginDataModelRealm.class).findAll().get(0).getVip_status() == null) {
            return vipStatus;
        }
        RealmResults<LiginDataModelRealm> realmMembers = realm.where(LiginDataModelRealm.class).findAll();
        vipStatus = realmMembers.get(0).getVip_status();
//        realm.commitTransaction();

        return vipStatus;
    }

    public static String getUserVipDateStart() {
//        config(context);
//        realm.beginTransaction();
        String vipDateStart = "";
//        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        if (realm.where(LiginDataModelRealm.class).findAll() == null) {
            return vipDateStart;
        } else if (realm.where(LiginDataModelRealm.class) == null) {
            return vipDateStart;
        } else if (realm.where(LiginDataModelRealm.class).findFirst() == null) {
            return vipDateStart;
        } else if (realm.where(LiginDataModelRealm.class).findAll().size() == 0) {
            return vipDateStart;
        }
        RealmResults<LiginDataModelRealm> realmMembers = realm.where(LiginDataModelRealm.class).findAll();
        vipDateStart = realmMembers.get(0).getVip_date_start();
//        realm.commitTransaction();
        return vipDateStart;
    }

    public static String getUserVipDateEnd() {
//        config(context);
//        realm.beginTransaction();
        String vipDateEnd = "";
//        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        if (realm.where(LiginDataModelRealm.class).findAll() == null) {
            return vipDateEnd;
        } else if (realm.where(LiginDataModelRealm.class) == null) {
            return vipDateEnd;
        } else if (realm.where(LiginDataModelRealm.class).findFirst() == null) {
            return vipDateEnd;
        } else if (realm.where(LiginDataModelRealm.class).findAll().size() == 0) {
            return vipDateEnd;
        }
        RealmResults<LiginDataModelRealm> realmMembers = realm.where(LiginDataModelRealm.class).findAll();
        vipDateEnd = realmMembers.get(0).getVip_date_end();
//        realm.commitTransaction();
        return vipDateEnd;
    }

    public static String getUserVipId() {
//        config(context);
//        realm.beginTransaction();
        String vipId = "";
//        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        if (realm.where(LiginDataModelRealm.class).findAll() == null) {
            return vipId;
        } else if (realm.where(LiginDataModelRealm.class) == null) {
            return vipId;
        } else if (realm.where(LiginDataModelRealm.class).findFirst() == null) {
            return vipId;
        } else if (realm.where(LiginDataModelRealm.class).findAll().size() == 0) {
            return vipId;
        }else  if (realm.where(LiginDataModelRealm.class).findFirst().getVip_id() == null) {
            return vipId;
        } else if (realm.where(LiginDataModelRealm.class).findAll().get(0).getVip_id() == null) {
            return vipId;
        }
        RealmResults<LiginDataModelRealm> realmMembers = realm.where(LiginDataModelRealm.class).findAll();
        vipId = realmMembers.get(0).getVip_id();
//        realm.commitTransaction();
        return vipId;
    }


    public static String getPositionRealm() {

        String status = "";
        Realm realm = Realm.getDefaultInstance();
        if (realm.where(LiginDataModelRealm.class).findAll() == null)
            return status;
        else {
            if (realm.where(LiginDataModelRealm.class).findAll().size() > 0) {
                status = realm.where(LiginDataModelRealm.class).findAll().get(0).getUser_status();
            }
        }
        return status;

    }

    public static String getVip() {

        String vip = "";
        Realm realm = Realm.getDefaultInstance();
        if (realm.where(LiginDataModelRealm.class).findAll() == null) {
            return vip;
        } else if (realm.where(LiginDataModelRealm.class) == null) {
            return vip;
        } else if (realm.where(LiginDataModelRealm.class).findFirst() == null) {
            return vip;
        } else if (realm.where(LiginDataModelRealm.class).findAll().size() == 0) {
            return vip;
        }
        if (realm.where(LiginDataModelRealm.class).findAll().size() > 0) {
            vip = realm.where(LiginDataModelRealm.class).findAll().get(0).getVip_status();
        }
        return vip;

    }

//    public static int addMemberRealm(final MemberModel loginModelReponse) {
////        config(context);
////        Realm.init(getApplicationContext());
//        Realm realm = Realm.getDefaultInstance();
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                if (loginModelReponse.getMemberDataReponseModel() != null) {
//                    LiginDataModelRealm loginModelRealm = realm.createObject(LiginDataModelRealm.class);
//                    loginModelRealm.setUser_id(loginModelReponse.getMemberDataReponseModel().getUser_id());
//                    loginModelRealm.setUser_user(loginModelReponse.getMemberDataReponseModel().getUser_user());
//                    loginModelRealm.setUser_status(loginModelReponse.getMemberDataReponseModel().getUser_status());
//                    loginModelRealm.setVip_id(loginModelReponse.getMemberDataReponseModel().getVip_id());
//                    loginModelRealm.setVip_status(loginModelReponse.getMemberDataReponseModel().getVip_status());
//                    loginModelRealm.setVip_date_start(loginModelReponse.getMemberDataReponseModel().getVip_date_start());
//                    loginModelRealm.setVip_date_end(loginModelReponse.getMemberDataReponseModel().getVip_date_end());
//
//
//                    realm.commitTransaction();
//                }
//            }
//        }, new Realm.Transaction.OnSuccess() {
//            @Override
//            public void onSuccess() {
//                realm.beginTransaction();
//                RealmResults<LiginDataModelRealm> result = realm.where(LiginDataModelRealm.class).findAll();
//                realm.commitTransaction();
//                count = result.size();
//
//            }
//        }, new Realm.Transaction.OnError() {
//            @Override
//            public void onError(Throwable error) {
////                 Toast.makeText(context,"Create user error", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        return count;
//    }

}
