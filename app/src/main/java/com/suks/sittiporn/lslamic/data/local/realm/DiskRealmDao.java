package com.suks.sittiporn.lslamic.data.local;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.suks.sittiporn.lslamic.data.remote.response.MemberModel;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.RealmQuery;

/**
 * Created by Weerawat on 7/6/2559.
 */
public class DiskRealmDao extends RealmDao {
    private static final String TAG = "DiskMemberRealmDao";

    public DiskRealmDao(Context context) {
        super(context);

    }

    public boolean getIDSswitch(final String idStatus) {
        boolean st = false;
        try {

            beginTransaction();
            RealmQuery<AlertModelRealm> memberDeviceRealmRealmQuery = where(AlertModelRealm.class);
            memberDeviceRealmRealmQuery.equalTo("id", idStatus);
            AlertModelRealm memberDeviceRealm = memberDeviceRealmRealmQuery.findFirst();

            if (memberDeviceRealm == null) {
                st = false;
            } else {
                st = Boolean.parseBoolean(memberDeviceRealm.getCheck());
            }
            commitTransaction();
//            Toast.makeText(context, "Create user setSuccess", Toast.LENGTH_SHORT).show();

//            actionEntity.setSuccess(true);
        } catch (Exception e) {
//            Toast.makeText(context, "Create user error", Toast.LENGTH_SHORT).show();
//            actionEntity.setSuccess(false);
        } finally {
            closeRealm();
        }
        return st;
    }

    public void saveS(final String idStatus, final String staus, final String sw, final Context context) {
        try {
            beginTransaction();

            RealmQuery<AlertModelRealm> memberDeviceRealmRealmQuery = where(AlertModelRealm.class);
            memberDeviceRealmRealmQuery.equalTo("id", idStatus);
            AlertModelRealm memberDeviceRealm = memberDeviceRealmRealmQuery.findFirst();

            if (memberDeviceRealm == null) {
                memberDeviceRealm = createObject(AlertModelRealm.class);
                memberDeviceRealm.setId(idStatus);
            }

            memberDeviceRealm.setCheck(staus);
            memberDeviceRealm.setSwitchDate(sw);
            memberDeviceRealm.setDate("22");

            commitTransaction();
//            Toast.makeText(context, "Create user setSuccess", Toast.LENGTH_SHORT).show();
//            actionEntity.setSuccess(true);
        } catch (Exception e) {
//            Toast.makeText(context, "Create user error", Toast.LENGTH_SHORT).show();
//            actionEntity.setSuccess(false);
        } finally {
            closeRealm();
        }
    }

    public void saveMember(final MemberModel loginModelReponse) {
        try {
            beginTransaction();
            RealmQuery<LiginDataModelRealm> memberDeviceRealmRealmQuery = where(LiginDataModelRealm.class);
            memberDeviceRealmRealmQuery.equalTo("id", loginModelReponse.getData().getId());
            LiginDataModelRealm memberDeviceRealm = memberDeviceRealmRealmQuery.findFirst();

            if (memberDeviceRealm == null) {
                memberDeviceRealm = createObject(LiginDataModelRealm.class);
                memberDeviceRealm.setId(loginModelReponse.getData().getId());
            }


            memberDeviceRealm.setEmail(loginModelReponse.getData().getEmail());
            memberDeviceRealm.setStatus(loginModelReponse.getData().getStatus());

            commitTransaction();
//            Toast.makeText(context, "Create user setSuccess", Toast.LENGTH_SHORT).show();
//            actionEntity.setSuccess(true);
        } catch (Exception e) {
//            Toast.makeText(context, "Create user error", Toast.LENGTH_SHORT).show();
//            actionEntity.setSuccess(false);
        } finally {
            closeRealm();
        }
    }
}
