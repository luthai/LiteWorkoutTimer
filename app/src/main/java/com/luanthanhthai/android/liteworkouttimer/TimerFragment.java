package com.luanthanhthai.android.liteworkouttimer;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * Created by Thai on 15.01.2016.
 * Copyright (c) [2016] [Luan Thanh Thai]
 * See the file LICENSE.txt for copying permission
 */
public class TimerFragment extends Fragment implements View.OnClickListener {

    private Button mPauseButton;
    private ViewGroup timerClockView;
    private TextView mMinutesView;
    private TextView mSecondsView;
    private ViewGroup keypadPanel;
    private ViewGroup pauseBarPanel;
    private int[] keypadButtons = {
                R.id.button_0, R.id.button_1, R.id.button_2,
                R.id.button_3, R.id.button_4, R.id.button_5,
                R.id.button_6, R.id.button_7, R.id.button_8,
                R.id.button_9
    };

    private int finalMinutesValue = 0;
    private int finalSecondsValue = 0;
    private boolean firstDigitHasValue = false;
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
        for (int keypadId : keypadButtons) {
            Button keypadButtonId = (Button) view.findViewById(keypadId);
            keypadButtonId.setOnClickListener(keypadListener);
        }

        // Keypad backspace button
        ImageButton mDelButton = (ImageButton) view.findViewById(R.id.button_del);
        mDelButton.setOnClickListener(this);

        // Timer clock view
        timerClockView = (ViewGroup) view.findViewById(R.id.timer_clock_view);

        // Digital timer view
        mMinutesView = (TextView) view.findViewById(R.id.timer_minutes_text_view);
        mSecondsView = (TextView) view.findViewById(R.id.timer_seconds_text_view);
        mMinutesView.setOnClickListener(timerTextViewListener);
        mSecondsView.setOnClickListener(timerTextViewListener);

        // For backwards compatibility set this
        // near the end
        setHasOptionsMenu(true);

        return view;
    }

    public void WorkoutTimer(long millisUntilFinished) {
        int countDownInterval = 1000;
        new CountDownTimer(millisUntilFinished, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                // Reset timer
                // or run next timer
                // (for now only reset timer)

            }
        }.start();
    }

    View.OnClickListener keypadListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int selectedButton = v.getId();
            for (int digit = 0; digit < keypadButtons.length; ++digit) {
                if (selectedButton == keypadButtons[digit]) {
                    if (mMinutesView.isSelected()) {
                        if (!firstDigitHasValue) {
                            setTimerClock(mMinutesView, true, digit);
                        } else {
                            setTimerClock(mMinutesView, false, digit);
                        }
                    } else if (mSecondsView.isSelected()) {
                        if (!firstDigitHasValue) {
                            setTimerClock(mSecondsView, true, digit);
                        } else {
                            setTimerClock(mSecondsView, false, digit);
                        }
                    }

                    return;
                }
            }
        }
    };

    public void setTimerClock(TextView selectedTimerView, boolean b, int i) {
        if (b) {
            firstDigitHasValue = true;
            finalSecondsValue = i;
        } else {
            firstDigitHasValue = false;
            finalSecondsValue = concatenateDigits(finalSecondsValue, i);
        }
        selectedTimerView.setText(String.format("%02d", finalSecondsValue));
    }

    public int concatenateDigits(int second, int first) {
        return (second * 10) + first;
    }

    View.OnClickListener timerTextViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView selectedView = (TextView) v;
            if (selectedView == mMinutesView) {
                selectTimerTextView(mMinutesView, mSecondsView);
            } else if (selectedView == mSecondsView) {
                selectTimerTextView(mSecondsView, mMinutesView);
            }
        }
    };

    public void selectTimerTextView(TextView selectView, TextView deselectView) {
        selectView.setSelected(true);
        deselectView.setSelected(false);
        selectView.setTextColor(getColor(getContext(), R.color.LightBlue_500));
        deselectView.setTextColor(getColor(getContext(), R.color.Black_opacity_87));
        firstDigitHasValue = false;
    }

    @SuppressWarnings("deprecation")
    public static int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.M) {
            return ContextCompat.getColor(context, id);
        } else {
            // getColor deprecated on Android Marshmallow(API 23)
            return context.getResources().getColor(id);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
                animSlidePanelDown(keypadPanel);
                animSlidePanelUp(pauseBarPanel);
                animSlideClockToCenter(timerClockView);
                isRunning = true;
                break;

            case R.id.button_rest:
                animSlidePanelDown(keypadPanel);
                animSlidePanelUp(pauseBarPanel);
                animSlideClockToCenter(timerClockView);
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
                animSlideClockUp(timerClockView);
                animSlidePanelDown(pauseBarPanel);
                animSlidePanelUp(keypadPanel);
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

    public void animSlideClockToCenter(ViewGroup slideClockToCenter) {
        Animation slideToCenter = AnimationUtils.loadAnimation(getContext(), R.anim.timer_clock_slide_to_center);
        slideClockToCenter.startAnimation(slideToCenter);
    }

    public void animSlideClockUp(ViewGroup slideClockUp) {
        Animation slideLayoutUp = AnimationUtils.loadAnimation(getContext(), R.anim.timer_clock_slide_up);
        slideClockUp.startAnimation(slideLayoutUp);
    }

    public void animSlidePanelUp(ViewGroup slidePanelUp) {
        Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.panel_slide_up);
        slidePanelUp.startAnimation(slideUp);
        slidePanelUp.setVisibility(View.VISIBLE);
    }

    public void animSlidePanelDown(ViewGroup slidePanelDown) {
        Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.panel_slide_down);
        slidePanelDown.startAnimation(slideDown);
        slidePanelDown.setVisibility(View.GONE);
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
