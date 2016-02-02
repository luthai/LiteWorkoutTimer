package com.luanthanhthai.android.liteworkouttimer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
public class TimerFragment extends Fragment implements View.OnClickListener {

    private Button mPauseButton;

    private ViewGroup keypadPanel;
    private ViewGroup pauseBarPanel;

    private boolean isRunning = false;

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

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        // The sliding panels
        keypadPanel = (ViewGroup) v.findViewById(R.id.start_button_bar_with_keypad);
        pauseBarPanel = (ViewGroup) v.findViewById(R.id.pause_button_bar);

        // Timer buttons
        Button startButton = (Button) v.findViewById(R.id.button_start);
        startButton.setOnClickListener(this);
        Button restButton = (Button) v.findViewById(R.id.button_rest);
        restButton.setOnClickListener(this);
        mPauseButton = (Button) v.findViewById(R.id.button_pause);
        mPauseButton.setOnClickListener(this);
        Button restartButton = (Button) v.findViewById(R.id.button_restart);
        restartButton.setOnClickListener(this);
        Button resetButton = (Button) v.findViewById(R.id.button_reset);
        resetButton.setOnClickListener(this);

        // Keypad numeric buttons



        // For backwards compatibility set this
        // near the end
        setHasOptionsMenu(true);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
                animSlidePanel(keypadPanel, pauseBarPanel);
                isRunning = true;
                break;

            case R.id.button_rest:
                animSlidePanel(keypadPanel, pauseBarPanel);
                isRunning = true;
                break;

            case R.id.button_pause:
                mPauseButton.setVisibility(View.INVISIBLE);
                isRunning = false;
                break;

            case R.id.button_restart:
                mPauseButton.setVisibility(View.VISIBLE);
                isRunning = true;
                break;

            case R.id.button_reset:
                animSlidePanel(pauseBarPanel, keypadPanel);
                isRunning = false;
                break;

            default:
                break;
        }
    }

    public void animSlidePanel(ViewGroup slidePanelDown, ViewGroup slidePanelUp) {
        // Slide panel down
        Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_bottom_down);

        slidePanelDown.startAnimation(slideDown);
        slidePanelDown.setVisibility(View.GONE);

        //Slide panel up
        Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_bottom_up);

        slidePanelUp.startAnimation(slideUp);
        slidePanelUp.setVisibility(View.VISIBLE);
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
