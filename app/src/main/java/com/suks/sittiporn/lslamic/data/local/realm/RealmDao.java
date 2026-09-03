package com.suks.sittiporn.lslamic.data.local;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;

/**
 * Created by Weerawat on 7/6/2559.
 */
public class RealmDao {
    protected Context context;
    private Realm realm;

    public RealmDao(Context context) {
        this.context = context;
        //realm = getRealm();
    }

    protected final synchronized Realm getRealm() {
        if (realm == null || realm.isClosed())
            realm = Realm.getDefaultInstance();
        return realm;
    }

    protected final synchronized void closeRealm() {
        if(realm == null)
            return;
        if (realm.isClosed())
            return;
        realm.close();
    }
    protected <E extends RealmModel> E createObject(Class<E> clazz){
        getRealm();
        return realm.createObject(clazz);
    }
    protected <E extends RealmModel> RealmQuery<E> where(Class<E> clazz) {
        getRealm();
        return realm.where(clazz);
    }

    protected final void beginTransaction(){
        getRealm();
        if(realm == null || realm.isClosed() || realm.isInTransaction())
            return;
        realm.beginTransaction();
    }
    protected final void commitTransaction(){
        if(realm == null || realm.isClosed())
            return;
        if(!realm.isInTransaction())
            return;
        realm.commitTransaction();
    }
}
