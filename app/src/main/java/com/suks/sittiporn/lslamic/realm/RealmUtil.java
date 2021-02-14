package com.suks.sittiporn.lslamic.realm;

import android.content.Context;
import android.widget.Toast;

import com.suks.sittiporn.lslamic.model.reponse.MemberModel;
import com.suks.sittiporn.lslamic.model.reponse.VipListDataResponseModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static io.realm.Realm.getDefaultInstance;

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
        Realm realm = getDefaultInstance();
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

    public static boolean getMember() {
        boolean member = false;
        Realm realm = getDefaultInstance();
        member = (realm.where(LiginDataModelRealm.class).findAll().size() > 0) ? true : false;
        return member;
    }

    public static String getMemberId() {
        String id = "";
        Realm realm = getDefaultInstance();
        id = (realm.where(LiginDataModelRealm.class).findAll().size() > 0) ? realm.where(LiginDataModelRealm.class).findFirst().getId() : "";
        return id;
    }
    public static String getEmail() {
        String email = "";
        Realm realm = getDefaultInstance();
        email = (realm.where(LiginDataModelRealm.class).findAll().size() > 0) ? realm.where(LiginDataModelRealm.class).findFirst().getEmail() : "";
        return email;
    }

    public static int deleteRealm(Context context) {
//        config(context);
//        realm.beginTransaction();
//        RealmResults<LiginDataModelRealm> Members = realm.where(LiginDataModelRealm.class).findAll();
//        Members.deleteAllFromRealm();
//        realm.commitTransaction();
        Realm realm = getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });

        return 0;

    }
//
//    public static String getMemberRealm() {
//        ;
//        String user_id = "";
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        if (realm.where(LiginDataModelRealm.class).findAll() == null) {
//            return user_id;
//        } else if (realm.where(LiginDataModelRealm.class) == null) {
//            return user_id;
//        }
//        RealmResults<LiginDataModelRealm> realmMembers = realm.where(LiginDataModelRealm.class).findAll();
//        realm.commitTransaction();
//        if (realmMembers.size() == 0)
//            return user_id;
//        user_id = realmMembers.get(0).getUser_id();
//        return user_id;
//
//    }

//    public static String getUserRealm() {
////        config(context);
////        realm.beginTransaction();
//        String user = "";
////        Realm.init(getApplicationContext());
//        Realm realm = Realm.getDefaultInstance();
//
//        if (realm.where(LiginDataModelRealm.class).findAll() == null) {
//            return user;
//        } else if (realm.where(LiginDataModelRealm.class) == null) {
//            return user;
//        } else if (realm.where(LiginDataModelRealm.class).findFirst() == null) {
//            return user;
//        } else if (realm.where(LiginDataModelRealm.class).findAll().size() == 0) {
//            return user;
//        }
//        RealmResults<LiginDataModelRealm> realmMembers = realm.where(LiginDataModelRealm.class).findAll();
//        user = realmMembers.get(0).getUser_user();
////        realm.commitTransaction();
//
//        return user;
//    }

//    public static String getUserVipStatus() {
////        config(context);
////        realm.beginTransaction();
//        String vipStatus = "";
////        Realm.init(getApplicationContext());
//        Realm realm = Realm.getDefaultInstance();
//        if (realm.where(LiginDataModelRealm.class).findAll() == null) {
//            return vipStatus;
//        } else if (realm.where(LiginDataModelRealm.class) == null) {
//            return vipStatus;
//        } else if (realm.where(LiginDataModelRealm.class).findFirst() == null) {
//            return vipStatus;
//        } else if (realm.where(LiginDataModelRealm.class).findAll().size() == 0) {
//            return vipStatus;
//        }else  if (realm.where(LiginDataModelRealm.class).findFirst().getVip_status() == null) {
//            return vipStatus;
//        } else if (realm.where(LiginDataModelRealm.class).findAll().get(0).getVip_status() == null) {
//            return vipStatus;
//        }
//        RealmResults<LiginDataModelRealm> realmMembers = realm.where(LiginDataModelRealm.class).findAll();
//        vipStatus = realmMembers.get(0).getVip_status();
////        realm.commitTransaction();
//
//        return vipStatus;
//    }

//    public static String getUserVipDateStart() {
////        config(context);
////        realm.beginTransaction();
//        String vipDateStart = "";
////        Realm.init(getApplicationContext());
//        Realm realm = Realm.getDefaultInstance();
//
//        if (realm.where(LiginDataModelRealm.class).findAll() == null) {
//            return vipDateStart;
//        } else if (realm.where(LiginDataModelRealm.class) == null) {
//            return vipDateStart;
//        } else if (realm.where(LiginDataModelRealm.class).findFirst() == null) {
//            return vipDateStart;
//        } else if (realm.where(LiginDataModelRealm.class).findAll().size() == 0) {
//            return vipDateStart;
//        }
//        RealmResults<LiginDataModelRealm> realmMembers = realm.where(LiginDataModelRealm.class).findAll();
//        vipDateStart = realmMembers.get(0).getVip_date_start();
////        realm.commitTransaction();
//        return vipDateStart;
//    }

//    public static String getUserVipDateEnd() {
////        config(context);
////        realm.beginTransaction();
//        String vipDateEnd = "";
////        Realm.init(getApplicationContext());
//        Realm realm = Realm.getDefaultInstance();
//
//        if (realm.where(LiginDataModelRealm.class).findAll() == null) {
//            return vipDateEnd;
//        } else if (realm.where(LiginDataModelRealm.class) == null) {
//            return vipDateEnd;
//        } else if (realm.where(LiginDataModelRealm.class).findFirst() == null) {
//            return vipDateEnd;
//        } else if (realm.where(LiginDataModelRealm.class).findAll().size() == 0) {
//            return vipDateEnd;
//        }
//        RealmResults<LiginDataModelRealm> realmMembers = realm.where(LiginDataModelRealm.class).findAll();
//        vipDateEnd = realmMembers.get(0).getVip_date_end();
////        realm.commitTransaction();
//        return vipDateEnd;
//    }

//    public static String getUserVipId() {
////        config(context);
////        realm.beginTransaction();
//        String vipId = "";
////        Realm.init(getApplicationContext());
//        Realm realm = Realm.getDefaultInstance();
//        if (realm.where(LiginDataModelRealm.class).findAll() == null) {
//            return vipId;
//        } else if (realm.where(LiginDataModelRealm.class) == null) {
//            return vipId;
//        } else if (realm.where(LiginDataModelRealm.class).findFirst() == null) {
//            return vipId;
//        } else if (realm.where(LiginDataModelRealm.class).findAll().size() == 0) {
//            return vipId;
//        }else  if (realm.where(LiginDataModelRealm.class).findFirst().getVip_id() == null) {
//            return vipId;
//        } else if (realm.where(LiginDataModelRealm.class).findAll().get(0).getVip_id() == null) {
//            return vipId;
//        }
//        RealmResults<LiginDataModelRealm> realmMembers = realm.where(LiginDataModelRealm.class).findAll();
//        vipId = realmMembers.get(0).getVip_id();
////        realm.commitTransaction();
//        return vipId;
//    }


//    public static String getPositionRealm() {
//
//        String status = "";
//        Realm realm = Realm.getDefaultInstance();
//        if (realm.where(LiginDataModelRealm.class).findAll() == null)
//            return status;
//        else {
//            if (realm.where(LiginDataModelRealm.class).findAll().size() > 0) {
//                status = realm.where(LiginDataModelRealm.class).findAll().get(0).getUser_status();
//            }
//        }
//        return status;
//
//    }
//
//    public static String getVip() {
//
//        String vip = "";
//        Realm realm = Realm.getDefaultInstance();
//        if (realm.where(LiginDataModelRealm.class).findAll() == null) {
//            return vip;
//        } else if (realm.where(LiginDataModelRealm.class) == null) {
//            return vip;
//        } else if (realm.where(LiginDataModelRealm.class).findFirst() == null) {
//            return vip;
//        } else if (realm.where(LiginDataModelRealm.class).findAll().size() == 0) {
//            return vip;
//        }
//        if (realm.where(LiginDataModelRealm.class).findAll().size() > 0) {
//            vip = realm.where(LiginDataModelRealm.class).findAll().get(0).getVip_status();
//        }
//        return vip;
//
//    }

    public static int addMemberRealm(final MemberModel loginModelReponse) {
//        config(context);
//        Realm.init(getApplicationContext());
        final Realm realm = getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (loginModelReponse.getData() != null) {
                    realm.beginTransaction();
                    LiginDataModelRealm loginModelRealm = realm.createObject(LiginDataModelRealm.class);
                    loginModelRealm.setId(loginModelReponse.getData().getId());
                    loginModelRealm.setEmail(loginModelReponse.getData().getEmail());
                    loginModelRealm.setStatus(loginModelReponse.getData().getStatus());
                    realm.commitTransaction();
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                realm.beginTransaction();
                RealmResults<LiginDataModelRealm> result = realm.where(LiginDataModelRealm.class).findAll();
                realm.commitTransaction();
                count = result.size();

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
//                 Toast.makeText(context,"Create user error", Toast.LENGTH_SHORT).show();

            }
        });

        return count;
    }


    public static int addAlertAlarmRealm(final String idStatus, final String staus, final String sw, final Context context) {

//        config(context);
        Realm.init(context);
        final Realm realm = getDefaultInstance();
//        Realm.init(context);
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .name(Realm.DEFAULT_REALM_NAME)
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        realm = Realm.getInstance(config);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.beginTransaction();
                AlertModelRealm loginModelRealm = realm.createObject(AlertModelRealm.class);
                loginModelRealm.setId(idStatus);
                loginModelRealm.setDate("2021-01-10");
                loginModelRealm.setSwitchDate(sw);
                loginModelRealm.setCheck(staus);
                realm.commitTransaction();

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                realm.beginTransaction();
                RealmResults<AlertModelRealm> result = realm.where(AlertModelRealm.class).findAll();
                realm.commitTransaction();
                count = result.size();

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                 Toast.makeText(context,"Create user error", Toast.LENGTH_SHORT).show();

            }
        });

        return count;
    }
}
