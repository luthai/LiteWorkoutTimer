package com.luanthanhthai.android.liteworkouttimer;


import android.support.v4.app.Fragment;


/**
 * Created by Thai on 15.01.2016.
 */
public class TimerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return TimerFragment.newInstance();
    }
}
