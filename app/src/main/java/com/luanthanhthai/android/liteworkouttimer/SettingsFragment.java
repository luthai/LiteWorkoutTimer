package com.luanthanhthai.android.liteworkouttimer;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

/**
 * Created by Thai on 03.02.2016.
 *
 */
public class SettingsFragment extends Fragment {
    private ViewGroup keypadPanel;
    private EditText mDelayStartTime;
    private EditText mDelayTimeMinutes;
    private EditText mDelayTimeSeconds;
    private EditText mRestTimeMinutes;
    private EditText mRestTimeSeconds;
    private EditText[] mArrayTimeView;
    private int[] keypadButtons = {
            R.id.button_0, R.id.button_1, R.id.button_2,
            R.id.button_3, R.id.button_4, R.id.button_5,
            R.id.button_6, R.id.button_7, R.id.button_8,
            R.id.button_9
    };

    private boolean firstDigitHasValue = false;

    private int delayStartTime = 0;
    private int delayTimeSeconds = 0;
    private int delayTimeMinutes = 0;
    private int restTimeSeconds = 0;
    private int restTimeMinutes = 0;


    //Timer color
    private final int timerActiveColor = R.color.LightBlue_500;
    private final int timerInactiveColor = R.color.Black_opacity_38;

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

        // Delay start timer
        mDelayStartTime = (EditText) view.findViewById(R.id.delay_start_time_edit);
        mDelayStartTime.setOnClickListener(delayStartTimerListener);

        // Delay timer
        mDelayTimeMinutes = (EditText) view.findViewById(R.id.delay_time_m_edit);
        mDelayTimeMinutes.setOnClickListener(delayTimerMinutesListener);
        mDelayTimeSeconds = (EditText) view.findViewById(R.id.delay_time_s_edit);
        mDelayTimeSeconds.setOnClickListener(delayTimerSecondsListener);

        //Rest timer
        mRestTimeMinutes = (EditText) view.findViewById(R.id.rest_time_m_edit);
        mRestTimeMinutes.setOnClickListener(restTimerMinutesListener);
        mRestTimeSeconds = (EditText) view.findViewById(R.id.rest_time_s_edit);
        mRestTimeSeconds.setOnClickListener(restTimerSecondsListener);

        mArrayTimeView = new EditText[5];
        mArrayTimeView[0] = mDelayStartTime;
        mArrayTimeView[1] = mDelayTimeMinutes;
        mArrayTimeView[2] = mDelayTimeSeconds;
        mArrayTimeView[3] = mRestTimeMinutes;
        mArrayTimeView[4] = mRestTimeSeconds;
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

        configureTimerViews(view);

        return view;
    }

    View.OnClickListener delayStartTimerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (keypadPanel.getVisibility() == View.GONE) {
                // To be coded
                // SetColor when selected
                // Set selected true and false for the other edittext
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
                    for (EditText currentView: mArrayTimeView) {
                        if (currentView.isSelected()) {
                            keypadTimerSet(currentView, digit);
                            break;
                        }
                    }

                    return;
                }
            }
        }
    };

    public void keypadTimerSet(EditText selectedTimerView, int digit) {
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
    public void setTimerClock(EditText selectedTimerView, boolean firstDigitEntered, int input) {
        if (firstDigitEntered) {
            firstDigitHasValue = true;
            setFirstDigit(selectedTimerView, input);
        } else {
            firstDigitHasValue = false;
            setFinalValue(selectedTimerView, input);
        }

        setTimerText(selectedTimerView, getFinalValue(selectedTimerView));
    }

    /**
     * Timer text update
     */
    public void setTimerText(EditText selectedTextView, long Millis) {
        selectedTextView.setText(String.format(Locale.getDefault(), FORMAT, Millis));
    }

    /**
     * User entered the first digit
     */
    public void setFirstDigit(EditText selectedTimerView, int value) {
        setValue(selectedTimerView, value);

    }

    /**
     * User entered the second digit
     */
    public void setFinalValue(EditText selectedTimerView, int value) {
        setValue(selectedTimerView, checkValidValue(
                concatenateDigits(getFinalValue(selectedTimerView), value)));
    }

    /**
     * Set value to correct view
     */
    public void setValue(EditText selectedTimerView, int value) {
        if (selectedTimerView == mDelayStartTime) {
            delayStartTime = value;
        } else if (selectedTimerView == mDelayTimeMinutes) {
            delayTimeMinutes = value;
        } else if (selectedTimerView == mDelayTimeSeconds) {
            delayTimeSeconds = value;
        } else if (selectedTimerView == mRestTimeMinutes) {
            restTimeMinutes = value;
        } else if (selectedTimerView == mRestTimeSeconds) {
            restTimeSeconds = value;
        }
    }

    /**
     * Return final user entered time value
     */
    public int getFinalValue(EditText selectedTimerView) {
        if (selectedTimerView == mDelayStartTime) {
            return delayStartTime;
        } else if (selectedTimerView == mDelayTimeMinutes) {
            return delayTimeMinutes;
        } else if (selectedTimerView == mDelayTimeSeconds) {
            return delayTimeSeconds;
        } else if (selectedTimerView == mRestTimeMinutes) {
            return restTimeMinutes;
        } else if (selectedTimerView == mRestTimeSeconds) {
            return restTimeSeconds;
        }

        return 0;
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
     * Return the selected timer view
     */
    public EditText getSelectedView(EditText selectedTimerView) {
        for (EditText currentView: mArrayTimeView) {
            if (currentView.isSelected()) {
                return currentView;
            }
        }

        return null;
    }

    /**
     * Set selected view and color,
     * and deselect the other
     */
    public void selectTimerTextView(EditText selectView) {
        for (EditText currentView: mArrayTimeView) {
            if (selectView == currentView) {
                selectView.setSelected(true);
                selectView.setTextColor(getColor(getContext(), timerActiveColor));
            } else {
                currentView.setSelected(false);
                currentView.setTextColor(getColor(getContext(), timerInactiveColor));
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
