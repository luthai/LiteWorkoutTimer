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
import android.widget.EditText;
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
        View v = inflater.inflate(R.layout.fragment_timer, container, false);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        // The sliding panels
        keypadPanel = (ViewGroup) v.findViewById(R.id.start_button_bar_with_keypad);
        pauseBarPanel = (ViewGroup) v.findViewById(R.id.pause_button_bar);

        // Edit Text
        editText = (EditText) v.findViewById(R.id.test_view);

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
        int[] keypadButtons = {
                                R.id.button_0, R.id.button_1, R.id.button_2,
                                R.id.button_3, R.id.button_4, R.id.button_5,
                                R.id.button_6, R.id.button_7, R.id.button_8,
                                R.id.button_9
                              };

        for (int i = 0; i < keypadButtons.length; ++i) {
            Button keypadButton = (Button) v.findViewById(keypadButtons[i]);
            final int finalI = i;
            keypadButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    editText.setText(String.valueOf(finalI));
                }
            });
        }


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
/*
            case R.id.button_0:
                editText.setText("0");
                break;

            case R.id.button_1:
                editText.setText("1");
                break;

            case R.id.button_2:
                editText.setText("2");
                break;

            case R.id.button_3:
                editText.setText("3");
                break;

            case R.id.button_4:
                editText.setText("4");
                break;

            case R.id.button_5:
                editText.setText("5");
                break;

            case R.id.button_6:
                editText.setText("6");
                break;

            case R.id.button_7:
                editText.setText("7");
                break;

            case R.id.button_8:
                editText.setText("8");
                break;

            case R.id.button_9:
                editText.setText("9");
                break;
*/
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
