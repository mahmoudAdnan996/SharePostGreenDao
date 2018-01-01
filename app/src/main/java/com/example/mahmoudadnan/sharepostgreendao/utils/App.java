package com.example.mahmoudadnan.sharepostgreendao.utils;

import android.app.Application;

import com.example.mahmoudadnan.sharepostgreendao.model.DaoMaster;
import com.example.mahmoudadnan.sharepostgreendao.model.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by mahmoud.adnan on 12/31/2017.
 */

public class App extends Application {

    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"posts-db");
        Database db = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return daoSession;
    }

}
