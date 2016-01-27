package com.luanthanhthai.android.liteworkouttimer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Thai on 27.01.2016.
 * Copyright (c) [2016] [Luan Thanh Thai]
 * See the file LICENSE.txt for copying permission
 */
public class TimerRunStateFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timer_run_state, null);
        return view;
    }
}
