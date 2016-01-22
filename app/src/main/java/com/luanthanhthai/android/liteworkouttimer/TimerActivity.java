package com.luanthanhthai.android.liteworkouttimer;


import android.support.v4.app.Fragment;


/**
 * Created by Thai on 15.01.2016.
 * Copyright (c) [2016] [Luan Thanh Thai]
 * See the file LICENSE.txt for copying permission
 */
public class TimerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return TimerFragment.newInstance();
    }
}
