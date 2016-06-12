package com.luanthanhthai.android.liteworkouttimer;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Thai on 13.05.2016.
 * Copyright (c) [2016] [Luan Thanh Thai]
 * See the file LICENSE.txt for copying permission
 */
public class RoutineSingleton {

    private static RoutineSingleton sSingleton;

    private List<Routine> mRoutines;

    private RoutineSingleton(Context context) {
        mRoutines = new ArrayList<>();

        for (int i = 0; i < 4; ++i) {
            Routine routine = new Routine();
            routine.setTitle("Routine #" + i);
            routine.setChosen(i % 2 == 0);
            mRoutines.add(routine);
        }
    }

    public static RoutineSingleton get(Context context) {
        if (sSingleton == null) {
            sSingleton = new RoutineSingleton(context);
        }

        return sSingleton;
    }

    public List<Routine> getRoutines() {
        return mRoutines;
    }

    public Routine getRoutine(UUID id) {
        for (Routine routine : mRoutines) {
            if (routine.getId().equals(id)) {
                return routine;
            }
        }
        return null;
    }
}
