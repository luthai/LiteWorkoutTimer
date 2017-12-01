package com.luanthanhthai.android.liteworkouttimer;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Thai on 14.04.2017.
 * Copyright (c) [2016] [Luan Thanh Thai]
 * See the file LICENSE.txt for copying permission
 */

public class SettingsSingleton {
    private static SettingsSingleton sSingleton;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private SettingsSingleton(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TimerDatabaseHelper(context).getWritableDatabase();
    }

    public static SettingsSingleton get(Context context) {
        if (sSingleton == null) {
            sSingleton = new SettingsSingleton(context);
        }

        return sSingleton;
    }


}
