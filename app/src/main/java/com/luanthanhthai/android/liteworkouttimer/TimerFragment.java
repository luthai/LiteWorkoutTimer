package com.luanthanhthai.android.liteworkouttimer;

import android.content.Context;
import android.graphics.PorterDuff;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Thai on 15.01.2016.
 * Copyright (c) [2016] [Luan Thanh Thai]
 * See the file LICENSE.txt for copying permission
 */
public class TimerFragment extends Fragment {

    private Toolbar mToolbar;
    private Button mStartButton;
    private Button mRestButton;
    private Button mPauseButton;
    private Button mResetButton;
    private Button mStartButton2;

    private ViewGroup keypadPanel;
    private ViewGroup pauseBar;

    private boolean isRunning;

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
        View v = inflater.inflate(R.layout.fragment_timer, container, false);

        mToolbar = (Toolbar) v.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        
        // Check if timer is running
        isRunning = false;

        // The sliding panels
        keypadPanel = (ViewGroup) v.findViewById(R.id.start_button_bar_with_keypad);
        pauseBar = (ViewGroup) v.findViewById(R.id.pause_button_bar);

        mStartButton = (Button) v.findViewById(R.id.button_start);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                keypadSlideDown();
                pauseBarSlideUP();
                isRunning = true;
            }
        });

        mRestButton = (Button) v.findViewById(R.id.button_rest);
        mRestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                keypadSlideDown();
                pauseBarSlideUP();
                isRunning = true;
            }
        });

        mPauseButton = (Button) v.findViewById(R.id.button_pause);
        mPauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mPauseButton.setVisibility(View.INVISIBLE);
                isRunning = false;
            }
        });

        mStartButton2 = (Button) v.findViewById(R.id.button_restart);
        mStartButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mPauseButton.setVisibility(View.VISIBLE);
                isRunning = true;
            }
        });

        mResetButton = (Button) v.findViewById(R.id.button_reset);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pauseBarSlideDown();
                keypadSlideUp();
                isRunning = false;
            }
        });

        // For backwards compatibility set this
        // near the end
        setHasOptionsMenu(true);

        return v;
    }

    public void keypadSlideUp() {
        Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_bottom_up);

        keypadPanel.startAnimation(slideUp);
        keypadPanel.setVisibility(View.VISIBLE);
    }

    public void keypadSlideDown() {
        Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_bottom_down);

        keypadPanel.startAnimation(slideDown);
        keypadPanel.setVisibility(View.GONE);
    }

    public void pauseBarSlideUP() {
        Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_bottom_up);

        pauseBar.startAnimation(slideUp);
        pauseBar.setVisibility(View.VISIBLE);
    }

    public void pauseBarSlideDown() {
        Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_bottom_down);

        pauseBar.startAnimation(slideDown);
        pauseBar.setVisibility(View.GONE);
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
