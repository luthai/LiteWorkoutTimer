package com.luanthanhthai.android.liteworkouttimer;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Thai on 03.02.2017.
 * Copyright (c) [2016] [Luan Thanh Thai]
 * See the file LICENSE.txt for copying permission
 */
public class SettingsFragment extends Fragment {
    private ViewGroup keypadPanel;
    private SwitchCompat mAudioSwitch;
    private SwitchCompat mRepeatSwitch;
    private EditText mDelayStartTime;
    private EditText mDelayTimeMinutes;
    private EditText mDelayTimeSeconds;
    private EditText mRestTimeMinutes;
    private EditText mRestTimeSeconds;
    private ArrayList<DigitsInput<EditText>> mListDigitsInput;
    private int[] keypadButtons = {
            R.id.button_0, R.id.button_1, R.id.button_2,
            R.id.button_3, R.id.button_4, R.id.button_5,
            R.id.button_6, R.id.button_7, R.id.button_8,
            R.id.button_9
    };

    // Timer color
    private final int timerActiveColor = R.color.LightBlue_500;
    private final int timerInactiveColor = R.color.Black_opacity_38;

    // Switch status
    private boolean audioStatus;
    private boolean repeatStatus;

    // Display digit format as 00
    final String FORMAT = "%02d";

    /**
     * All settings widgets initialized
     */
    public void configureTimerViews(View view) {
        keypadPanel = (ViewGroup) view.findViewById(R.id.keypad_settings);

        // Keypad numeric buttons
        for (int keypadId : keypadButtons) {
            Button keypadButtonId = (Button) view.findViewById(keypadId);
            keypadButtonId.setOnClickListener(keypadListener);
        }

        // Audio switch
        mAudioSwitch = (SwitchCompat) view.findViewById(R.id.audio_switch);
        mAudioSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                audioStatus = b;
            }
        });

        // Repeat switch
        mRepeatSwitch = (SwitchCompat) view.findViewById(R.id.repeat_switch);
        mRepeatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                repeatStatus = b;
            }
        });

        // Delay start timer
        mDelayStartTime = (EditText) view.findViewById(R.id.delay_start_time_edit);
        mDelayStartTime.setOnClickListener(delayStartTimerListener);

        // Delay timer
        mDelayTimeMinutes = (EditText) view.findViewById(R.id.delay_time_m_edit);
        mDelayTimeMinutes.setOnClickListener(delayTimerMinutesListener);
        mDelayTimeSeconds = (EditText) view.findViewById(R.id.delay_time_s_edit);
        mDelayTimeSeconds.setOnClickListener(delayTimerSecondsListener);

        // Rest timer
        mRestTimeMinutes = (EditText) view.findViewById(R.id.rest_time_m_edit);
        mRestTimeMinutes.setOnClickListener(restTimerMinutesListener);
        mRestTimeSeconds = (EditText) view.findViewById(R.id.rest_time_s_edit);
        mRestTimeSeconds.setOnClickListener(restTimerSecondsListener);

        // Initialize digit objects
        DigitsInput<EditText> delay = new DigitsInput<>(mDelayStartTime, 0);
        DigitsInput<EditText> delayminutes = new DigitsInput<>(mDelayTimeMinutes, 0);
        DigitsInput<EditText> delayseconds = new DigitsInput<>(mDelayTimeSeconds, 0);
        DigitsInput<EditText> restminutes = new DigitsInput<>(mRestTimeMinutes, 0);
        DigitsInput<EditText> restseconds = new DigitsInput<>(mRestTimeSeconds, 0);

        mListDigitsInput = new ArrayList<>();
        mListDigitsInput.add(delay);
        mListDigitsInput.add(delayminutes);
        mListDigitsInput.add(delayseconds);
        mListDigitsInput.add(restminutes);
        mListDigitsInput.add(restseconds);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        // Load instance state
        if (savedInstanceState != null) {
            repeatStatus = savedInstanceState.getBoolean("REPEAT_STATE");
            audioStatus = savedInstanceState.getBoolean("AUDIO_STATE");
        }

        configureTimerViews(view);

        return view;
    }

    /**
     * Save instance state
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("REPEAT_STATE", repeatStatus);
        savedInstanceState.putBoolean("AUDIO_STATE", audioStatus);
    }

    /**
     * Pause activity
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    View.OnClickListener delayStartTimerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (keypadPanel.getVisibility() == View.GONE) {
                animSlidePanelUp(keypadPanel);
            }

            EditText selectedView = (EditText) view;
            selectTimerTextView(selectedView);
        }
    };

    View.OnClickListener delayTimerMinutesListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (keypadPanel.getVisibility() == View.GONE) {
                animSlidePanelUp(keypadPanel);
            }

            EditText selectedView = (EditText) view;
            selectTimerTextView(selectedView);
        }
    };

    View.OnClickListener delayTimerSecondsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (keypadPanel.getVisibility() == View.GONE) {
                animSlidePanelUp(keypadPanel);
            }

            EditText selectedView = (EditText) view;
            selectTimerTextView(selectedView);
        }
    };

    View.OnClickListener restTimerMinutesListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (keypadPanel.getVisibility() == View.GONE) {
                animSlidePanelUp(keypadPanel);
            }

            EditText selectedView = (EditText) view;
            selectTimerTextView(selectedView);
        }
    };

    View.OnClickListener restTimerSecondsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (keypadPanel.getVisibility() == View.GONE) {
                animSlidePanelUp(keypadPanel);
            }

            EditText selectedView = (EditText) view;
            selectTimerTextView(selectedView);
        }
    };

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
                    for (DigitsInput<EditText> currentView: mListDigitsInput) {
                        if (currentView.getView().isSelected()) {
                            currentView.setDigits(digit);
                            setTimerText(currentView.getView(), currentView.getDigits());
                            break;
                        }
                    }

                    return;
                }
            }
        }
    };

    /**
     * Timer text update
     */
    public void setTimerText(EditText selectedTextView, long Millis) {
        selectedTextView.setText(String.format(Locale.getDefault(), FORMAT, Millis));
    }

    /**
     * Set selected view and color,
     * and deselect the other
     */
    public void selectTimerTextView(EditText selectView) {
        for (DigitsInput<EditText> currentView: mListDigitsInput) {
            if (selectView == currentView.getView()) {
                selectView.setSelected(true);
                selectView.setTextColor(getColor(getContext(), timerActiveColor));
            } else {
                currentView.getView().setSelected(false);
                currentView.getView().setTextColor(getColor(getContext(), timerInactiveColor));
            }
        }
    }

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
}
