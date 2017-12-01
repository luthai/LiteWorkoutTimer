package com.luanthanhthai.android.liteworkouttimer;

/**
 * Created by Thai on 13.04.2017.
 * Copyright (c) [2016] [Luan Thanh Thai]
 * See the file LICENSE.txt for copying permission
 */

public class SettingsDbSchema {
    public static final class SettingsTable {
        public static final String NAME = "settings";

        public static final String CREATE_SETTINGS_TABLE = "CREATE TABLE "
                + NAME + "(" + Cols.KEY_ID + "INTEGER PRIMARY KEY"
                + Cols.TITLE  + ", " + Cols.ENABLED + ")";

        public static final class Cols {
            public static final String KEY_ID = "id";
            public static final String TITLE = "title";
            public static final String ENABLED = "enabled";
        }


    }
}
