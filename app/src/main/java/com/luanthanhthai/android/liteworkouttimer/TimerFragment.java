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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

    private EditText editText;

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
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        // The sliding panels
        keypadPanel = (ViewGroup) view.findViewById(R.id.start_button_bar_with_keypad);
        pauseBarPanel = (ViewGroup) view.findViewById(R.id.pause_button_bar);

        // Edit Text
        editText = (EditText) view.findViewById(R.id.test_view);

        // Timer buttons
        Button startButton = (Button) view.findViewById(R.id.button_start);
        startButton.setOnClickListener(this);
        Button restButton = (Button) view.findViewById(R.id.button_rest);
        restButton.setOnClickListener(this);
        mPauseButton = (Button) view.findViewById(R.id.button_pause);
        mPauseButton.setOnClickListener(this);
        Button restartButton = (Button) view.findViewById(R.id.button_restart);
        restartButton.setOnClickListener(this);
        Button resetButton = (Button) view.findViewById(R.id.button_reset);
        resetButton.setOnClickListener(this);

        // Keypad numeric buttons
        int[] keypadButtons = {
                                R.id.button_0, R.id.button_1, R.id.button_2,
                                R.id.button_3, R.id.button_4, R.id.button_5,
                                R.id.button_6, R.id.button_7, R.id.button_8,
                                R.id.button_9
                              };

        for (int i = 0; i < keypadButtons.length; ++i) {
            Button keypadButton = (Button) view.findViewById(keypadButtons[i]);
            final int finalI = i;
            keypadButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    editText.setText(String.valueOf(finalI));
                }
            });
        }

        // Keypad backspace button
        ImageButton mDelButton = (ImageButton) view.findViewById(R.id.button_del);
        mDelButton.setOnClickListener(this);


        // For backwards compatibility set this
        // near the end
        setHasOptionsMenu(true);

        return view;
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
                mPauseButton.setVisibility(View.VISIBLE);
                isRunning = false;
                break;

            case R.id.button_del:
                // Backspace insert later
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_timer, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment;

        switch (item.getItemId()) {
            case R.id.menu_ic_create_routine:
                fragment = new CreateRoutinesFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
                return true;

            case R.id.menu_ic_settings:
                fragment = new SettingsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
