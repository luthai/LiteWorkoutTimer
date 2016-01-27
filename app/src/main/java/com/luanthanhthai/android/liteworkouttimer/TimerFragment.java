package com.luanthanhthai.android.liteworkouttimer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Thai on 15.01.2016.
 * Copyright (c) [2016] [Luan Thanh Thai]
 * See the file LICENSE.txt for copying permission
 */
public class TimerFragment extends Fragment {

    final static String TAG_RESET_STATE = "fragment_reset_state";
    final static String TAG_RUN_STATE = "fragment_run_state";
    final static String KEY_MSG_RESET_STATE = "fragment_reset_key_msg";
    final static String KEY_MSG_RUN_STATE = "fragment_run_key_msg";

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TimerResetStateFragment resetStateFragment;

    private Toolbar mToolbar;

    public static TimerFragment newInstance() {
        return new TimerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);

        // Add resetStateFragment to TimerFragment
        fragmentManager = getFragmentManager();
        resetStateFragment = new TimerResetStateFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                            .add(R.id.timer_keypad_container, resetStateFragment, TAG_RESET_STATE)
                            .commit();

        // For backwards compatibility set this
        // near the end
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_timer, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = getActivity();
        CharSequence text;
        int duration = Toast.LENGTH_SHORT;
        Toast toast;

        switch (item.getItemId()) {
            case R.id.menu_ic_create_routine:
                text = "Create routine";
                toast = Toast.makeText(context, text, duration);
                toast.show();
                return true;
            case R.id.menu_ic_settings:
                text = "Settings";
                toast = Toast.makeText(context, text, duration);
                toast.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
