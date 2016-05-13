package com.luanthanhthai.android.liteworkouttimer;

import android.content.Context;

/**
 * Created by Thai on 13.05.2016.
 * Copyright (c) [2016] [Luan Thanh Thai]
 * See the file LICENSE.txt for copying permission
 */
public class RoutineSingleton {

    private static RoutineSingleton sSingleton;

    private RoutineSingleton(Context context) {
        
    }

    public static RoutineSingleton get(Context context) {
        if (sSingleton == null) {
            sSingleton = new RoutineSingleton(context);
        }

        return sSingleton;
    }
}
