package com.luanthanhthai.android.liteworkouttimer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;


/**
 * Created by Thai on 15.01.2016.
 * Copyright (c) [2016] [Luan Thanh Thai]
 * See the file LICENSE.txt for copying permission
 */
public class TimerFragment extends Fragment implements View.OnClickListener {

    private Button mStartButton;
    private Button mPauseButton;
    private TextView mMinutesView;
    private TextView mSecondsView;
    private TextView mColonView;
    private ViewGroup timerClockView;
    private ViewGroup keypadPanel;
    private ViewGroup pauseBarPanel;
    private int[] keypadButtons = {
                R.id.button_0, R.id.button_1, R.id.button_2,
                R.id.button_3, R.id.button_4, R.id.button_5,
                R.id.button_6, R.id.button_7, R.id.button_8,
                R.id.button_9
    };

    private MyTimer userInputTimer;
    private final long countDownInterval = 250;
    private long totalMillis = 0;
    private long pausedMillis = 0;
    private int finalMinutesValue = 0;
    private int finalSecondsValue = 0;
    private final int timerColor = R.color.Black_opacity_87;
    private final int delayColor = R.color.Black_opacity_54;
    private final int restColor = R.color.LightBlue_500;

    private boolean firstDigitHasValue = false;
    private boolean enableRepeat;
    private boolean enableDelay;
    private boolean isDelayRunning = false;
    private boolean isStartPressed = false;

    private long timerDelayMillis = 3 * 1000;

    private final int animTimerDuration = 500;
    private float yOriginalValue;
    private float yCenterValue;

    final String FORMAT = "%02d";


    public static TimerFragment newInstance() {
        return new TimerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * All timer widgets initialized
     */
    public void configureTimerViews(View view) {
        // The sliding panels
        keypadPanel = (ViewGroup) view.findViewById(R.id.start_button_bar_with_keypad);
        pauseBarPanel = (ViewGroup) view.findViewById(R.id.pause_button_bar);

        // Timer buttons
        mStartButton = (Button) view.findViewById(R.id.button_start);
        mStartButton.setOnClickListener(this);
        Button restButton = (Button) view.findViewById(R.id.button_rest);
        restButton.setOnClickListener(this);
        mPauseButton = (Button) view.findViewById(R.id.button_pause);
        mPauseButton.setOnClickListener(this);
        Button remStartButton = (Button) view.findViewById(R.id.button_restart);
        remStartButton.setOnClickListener(this);
        Button resetButton = (Button) view.findViewById(R.id.button_reset);
        resetButton.setOnClickListener(this);

        // Keypad numeric buttons
        for (int keypadId : keypadButtons) {
            Button keypadButtonId = (Button) view.findViewById(keypadId);
            keypadButtonId.setOnClickListener(keypadListener);
        }

        // Backspace button
        ImageButton backspaceButton = (ImageButton) view.findViewById(R.id.button_backspace);
        backspaceButton.setOnClickListener(keyBackspaceListener);

        // Digital timer view
        mMinutesView = (TextView) view.findViewById(R.id.timer_minutes_text_view);
        mSecondsView = (TextView) view.findViewById(R.id.timer_seconds_text_view);
        mColonView = (TextView) view.findViewById(R.id.timer_colon_view);
        mMinutesView.setOnClickListener(timerTextViewListener);
        mSecondsView.setOnClickListener(timerTextViewListener);

        // Timer text view
        timerClockView = (ViewGroup) view.findViewById(R.id.timer_clock_view);
        timerClockView.getViewTreeObserver().addOnGlobalLayoutListener(new MyGlobalListenerClass());

        // Repeat icon
        ImageButton repeatButton = (ImageButton) view.findViewById(R.id.timer_repeat);
        repeatButton.setOnClickListener(repeatButtonListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        configureTimerViews(view);

        disableStartButton();
        enableDelay = true;

        // For backwards compatibility set this
        // near the end
        setHasOptionsMenu(true);

        return view;
    }

    /**
     * Pause activity
     */
    @Override
    public void onPause() {
        super.onPause();


    }

    /**
     * Initialize y coordinates for sliding animation
     */
    class MyGlobalListenerClass implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            // Remove the ViewTree
            timerClockView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            /*
            Log.d("ADebugTag", "YScreenheight: " + String.valueOf(getScreenHeight()));
            Log.d("ADebugTag", "YCenterValue: " + String.valueOf(calcCenterYValue()));
            Log.d("ADebugTag", "Width: "    + String.valueOf(timerClockView.getWidth()));
            Log.d("ADebugTag", "Height: "   + String.valueOf(timerClockView.getHeight()));
            Log.d("ADebugTag", "Top: "      + String.valueOf(timerClockView.getTop()));
            Log.d("ADebugTag", "Bottom: "   + String.valueOf(timerClockView.getBottom()));
            Log.d("ADebugTag", "Y: "        + String.valueOf(timerClockView.getY()));
            Log.d("ADebugTag", "X: "        + String.valueOf(timerClockView.getX()));
            */

            if (getOrientation() == Configuration.ORIENTATION_PORTRAIT) {
                yOriginalValue = timerClockView.getY();
                yCenterValue = calcCenterYValue();
            } else {
                yOriginalValue = timerClockView.getY();
                yCenterValue = calcCenterYValue();
            }
        }
    }

    /**
     * Get orientation of device
     */
    public int getOrientation() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)
                getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        if (metrics.widthPixels < metrics.heightPixels) {
            return Configuration.ORIENTATION_PORTRAIT;
        } else {
            return Configuration.ORIENTATION_LANDSCAPE;
        }
    }

    /**
     * Timer class with onFinish algorithm,
     * for running the timer accordingly when finished
     */
    public class MyTimer extends CountDownTimer {
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            pausedMillis = millisUntilFinished;

            mMinutesView.setText(String.format(Locale.getDefault(),FORMAT,
                    millisToMinutes(millisUntilFinished)));
            mSecondsView.setText(String.format(Locale.getDefault(),FORMAT,
                    getRemainderSeconds(millisUntilFinished)));
        }

        @Override
        public void onFinish() {
            if (isStartPressed) {
                if (enableRepeat) {
                    if (isDelayRunning) {
                        isDelayRunning = false;
                        setTimer(totalMillis);
                        switchTimeColor(timerColor);
                    } else {
                        isDelayRunning = true;
                        setTimer(timerDelayMillis);
                        switchTimeColor(delayColor);
                    }
                } else {
                    if (isDelayRunning) {
                        isDelayRunning = false;
                        setTimer(totalMillis);
                        switchTimeColor(timerColor);
                    } else {
                        timerReset();
                    }
                }
            } else {
                timerReset();
            }
        }
    }

    /**
     * Run timer according to inputted time
     */
    public void setTimer(long runTime) {
        userInputTimer = new MyTimer(runTime, countDownInterval);
        userInputTimer.start();
    }

    /**
     * Run timer according to which button press,
     * and if delay is enabled
     */
    public void runTimer() {
        if (isStartPressed) {
            if (enableDelay) {
                isDelayRunning = true;
                userInputTimer = new MyTimer(timerDelayMillis, countDownInterval);
                switchTimeColor(delayColor);
            } else {
                isDelayRunning = false;
                userInputTimer = new MyTimer(totalMillis, countDownInterval);
                switchTimeColor(timerColor);
            }
        } else {
            long timerRestMillis = 2 * 60 * 1000;
            userInputTimer = new MyTimer(timerRestMillis, countDownInterval);
        }

        userInputTimer.start();
    }

    public long millisToMinutes(long millis) {
        return TimeUnit.MILLISECONDS.toMinutes(millis);
    }

    /**
     * Seconds remain = Millis to seconds - minutes to seconds
     */
    public long getRemainderSeconds(long millisUntilFinished) {
        return TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
               TimeUnit.MINUTES.toSeconds(millisToMinutes(millisUntilFinished));
    }

    public long clockTimeToMillis(int minutes, int seconds) {
        return TimeUnit.MINUTES.toMillis(minutes) +
               TimeUnit.SECONDS.toMillis(seconds);
    }

    View.OnClickListener repeatButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setSelected(!v.isSelected());

            enableRepeat = v.isSelected();
        }
    };

    /**
     * Backspace button,
     * delete last entered digit
     */
    View.OnClickListener keyBackspaceListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mMinutesView.isSelected()) {
                backspaceCondition(mMinutesView);
            } else if (mSecondsView.isSelected()) {
                backspaceCondition(mSecondsView);
            }
        }
    };

    /**
     * Algorithm for backspace button
     */
    public void backspaceCondition(TextView selectedTextView) {
        if (getFinalValue(selectedTextView) < 10) {
            setFirstDigit(selectedTextView, 0);
            firstDigitHasValue = false;
        } else {
            setFirstDigit(selectedTextView, getFinalValue(selectedTextView) / 10);
            firstDigitHasValue = true;
        }

        selectedTextView.setText(String.format(Locale.getDefault(), FORMAT, getFinalValue(selectedTextView)));
    }

    /**
     * Keypad listener whether minutes or seconds is selected,
     * and take in user inputted time and display it
     */
    View.OnClickListener keypadListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int selectedButton = v.getId();
            for (int digit = 0; digit < keypadButtons.length; ++digit) {
                if (selectedButton == keypadButtons[digit]) {
                    if (mMinutesView.isSelected()) {
                        keypadTimerSet(mMinutesView, digit);
                    } else if (mSecondsView.isSelected()) {
                        keypadTimerSet(mSecondsView, digit);
                    }

                    return;
                }
            }
        }
    };

    public void keypadTimerSet(TextView selectedTimerView, int digit) {
        if (!firstDigitHasValue) {
            setTimerClock(selectedTimerView, true, digit);
        } else {
            setTimerClock(selectedTimerView, false, digit);
        }
    }

    /**
     * Algorithm for user inputted digits,
     * and save the total time
     */
    public void setTimerClock(TextView selectedTimerView, boolean firstDigitEntered, int input) {
        if (firstDigitEntered) {
            firstDigitHasValue = true;
            setFirstDigit(selectedTimerView, input);
        } else {
            firstDigitHasValue = false;
            setFinalValue(selectedTimerView, input);

            if (selectedTimerView == mMinutesView) {
                selectTimerTextView(mSecondsView, mMinutesView);
            }
        }

        totalMillis = clockTimeToMillis(finalMinutesValue, finalSecondsValue);
        selectedTimerView.setText(String.format(Locale.getDefault(), FORMAT, getFinalValue(selectedTimerView)));
    }

    /**
     * User entered the first digit
     */
    public void setFirstDigit(TextView selectedTimerView, int value) {
        if (selectedTimerView == mMinutesView) {
            finalMinutesValue = value;
        } else {
            finalSecondsValue = value;
        }

        disableStartButton();
    }

    /**
     * User entered the second digit
     */
    public void setFinalValue(TextView selectedTimerView, int value) {
        if (selectedTimerView == mMinutesView) {
            finalMinutesValue = checkValidValue(
                    concatenateDigits(finalMinutesValue, value));
        } else {
            finalSecondsValue = checkValidValue(
                    concatenateDigits(finalSecondsValue, value));
        }

        disableStartButton();
    }

    /**
     * Return final user entered time value
     */
    public int getFinalValue(TextView selectedTimerView) {
        if (selectedTimerView == mMinutesView) {
            return finalMinutesValue;
        } else {
            return finalSecondsValue;
        }
    }

    /**
     * Concatenate the first digit with the second digit
     */
    public int concatenateDigits(int second, int first) {
        return (second * 10) + first;
    }

    /**
     * Check if user inputted value is less than 60
     */
    public int checkValidValue(int userInput) {
        if (userInput < 60) {
            return userInput;
        } else {
            return 59;
        }
    }

    /**
     * Listen to whether time minutes or seconds is selected
     */
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

    /**
     * Display selected time with color,
     * and deselect the other
     */
    public void selectTimerTextView(TextView selectView, TextView deselectView) {
        selectView.setSelected(true);
        deselectView.setSelected(false);
        selectView.setTextColor(getColor(getContext(), restColor));
        deselectView.setTextColor(getColor(getContext(), timerColor));
        firstDigitHasValue = false;
    }

    /**
     * Switch timer text color,
     * for minutes, colon and seconds
     */
    public void switchTimeColor(int colorId) {
        mMinutesView.setTextColor(getColor(getContext(), colorId));
        mColonView.setTextColor(getColor(getContext(), colorId));
        mSecondsView.setTextColor(getColor(getContext(), colorId));
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

    /**
     * Disable clickable timer clock,
     * when timer is running
     */
    public void disableClickableTimer() {
        mMinutesView.setClickable(false);
        mSecondsView.setClickable(false);
    }

    /**
     * Enable clickable timer clock,
     * when timer ends
     */
    public void enableClickableTimer() {
        mMinutesView.setClickable(true);
        mSecondsView.setClickable(true);
    }

    /**
     * Disable/enable start button,
     * when timer condition is met
     */
    public void disableStartButton() {
        if (finalMinutesValue == 0 && finalSecondsValue == 0) {
            mStartButton.setClickable(false);
            mStartButton.setBackgroundColor(getColor(getContext(), R.color.Black_opacity_38));
        } else {
            mStartButton.setClickable(true);
            mStartButton.setBackgroundColor(getColor(getContext(), R.color.Green_500));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
                timerStart();
                disableClickableTimer();
                break;

            case R.id.button_rest:
                timerRest();
                disableClickableTimer();
                break;

            case R.id.button_pause:
                timerPause();
                break;

            case R.id.button_restart:
                timerRestart();
                break;

            case R.id.button_reset:
                timerReset();
                break;

            default:
                break;
        }
    }

    public void timerStart() {
        animSlidePanelDown(keypadPanel);
        animSlidePanelUp(pauseBarPanel);
        animSlideClockToCenter();

        isStartPressed = true;
        runTimer();
    }

    public void timerRest() {
        animSlidePanelDown(keypadPanel);
        animSlidePanelUp(pauseBarPanel);
        animSlideClockToCenter();

        isStartPressed = false;
        runTimer();
        switchTimeColor(restColor);

    }

    public void timerPause() {
        mPauseButton.setVisibility(View.INVISIBLE);

        userInputTimer.cancel();
    }

    public void timerRestart() {
        mPauseButton.setVisibility(View.VISIBLE);

        setTimer(pausedMillis);
    }

    public void timerReset() {
        animSlideClockUp();
        animSlidePanelDown(pauseBarPanel);
        animSlidePanelUp(keypadPanel);
        mPauseButton.setVisibility(View.VISIBLE);

        userInputTimer.cancel();
        mMinutesView.setText(String.format(Locale.getDefault(), FORMAT, finalMinutesValue));
        mSecondsView.setText(String.format(Locale.getDefault(), FORMAT, finalSecondsValue));
        switchTimeColor(timerColor);
        enableClickableTimer();

        mMinutesView.setSelected(false);
        mSecondsView.setSelected(false);
    }

    /**
     * Calculate y slide to center value
     * Portrait: 75% of timer clock view above center
     * Landscape: 60% of timer clock view above center
     */
    public float calcCenterYValue() {
        if (getOrientation() == Configuration.ORIENTATION_PORTRAIT) {
            return (getScreenHeight() / 2) - (timerClockView.getHeight() * 0.75f);
        } else  {
            return (getScreenHeight() / 2) - (timerClockView.getHeight() * 0.60f);
        }
    }

    /**
     * Get screen center height
     */
    public int getScreenHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = getActivity().getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;
    }

    /**
     * Property Animation
     * Animation for timer text slide to center
     */
    public void animSlideClockToCenter() {
        timerClockView.animate().setDuration(animTimerDuration);

        if (getOrientation() == Configuration.ORIENTATION_PORTRAIT) {
            timerClockView.animate().y(yCenterValue);
        } else {
            ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(timerClockView, "scaleY", 1.5f);
            ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(timerClockView, "scaleX", 1.5f);
            scaleUpY.setDuration(animTimerDuration);
            scaleUpX.setDuration(animTimerDuration);
            AnimatorSet scaleUp = new AnimatorSet();
            scaleUp.play(scaleUpX).with(scaleUpY);

            timerClockView.animate().y(yCenterValue);
            scaleUp.start();
        }

    }

    /**
     * Property Animation
     * Animation for timer clock slide up to original position
     */
    public void animSlideClockUp() {
        timerClockView.animate().setDuration(animTimerDuration);


        if (getOrientation() == Configuration.ORIENTATION_PORTRAIT) {
            timerClockView.animate().y(yOriginalValue);
        } else {
            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(timerClockView, "scaleY", 1f);
            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(timerClockView, "scaleX", 1f);
            scaleDownY.setDuration(animTimerDuration);
            scaleDownX.setDuration(animTimerDuration);
            AnimatorSet scaleDown = new AnimatorSet();
            scaleDown.play(scaleDownX).with(scaleDownY);

            timerClockView.animate().y(yOriginalValue);
            scaleDown.start();
        }

    }

    /**
     * Animation for keypad slide up from bottom
     */
    public void animSlidePanelUp(ViewGroup slidePanelUp) {
        Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.panel_slide_up);
        slidePanelUp.startAnimation(slideUp);
        slidePanelUp.setVisibility(View.VISIBLE);
    }

    /**
     * Animation for keypad slide down and disappear
     */
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
