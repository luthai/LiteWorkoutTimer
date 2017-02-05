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
import android.widget.EditText;

/**
 * Created by Thai on 03.02.2016.
 *
 */
public class SettingsFragment extends Fragment {
    private ViewGroup keypadPanel;
    private EditText delayStartTimerSeconds;
    private EditText delayTimerMinutes;
    private EditText delayTimerSeconds;
    private EditText restTimerMinutes;
    private EditText restTimerSeconds;


    //Timer color
    private final int digitsSelected = R.color.LightBlue_500;
    private final int digitsUnselected = R.color.Black_opacity_38;

    /**
     * All settings widgets initialized
     */
    public void configureTimerViews(View view) {
        keypadPanel = (ViewGroup) view.findViewById(R.id.keypad_settings);

        delayStartTimerSeconds = (EditText) view.findViewById(R.id.delay_start_time_edit);
        delayStartTimerSeconds.setOnClickListener(delayStartTimerListener);
        delayTimerMinutes = (EditText) view.findViewById(R.id.delay_time_m_edit);
        delayTimerMinutes.setOnClickListener(delayTimerMinutesListener);
        delayTimerSeconds = (EditText) view.findViewById(R.id.delay_time_s_edit);
        delayTimerSeconds.setOnClickListener(delayTimerSecondsListener);
        restTimerMinutes = (EditText) view.findViewById(R.id.rest_time_m_edit);
        restTimerMinutes.setOnClickListener(restTimerMinutesListener);
        restTimerSeconds = (EditText) view.findViewById(R.id.rest_time_s_edit);
        restTimerSeconds.setOnClickListener(restTimerSecondsListener);

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
            if (keypadPanel.getVisibility() == View.INVISIBLE) {
                animSlidePanelUp(keypadPanel);
            }
        }
    };

    View.OnClickListener delayTimerMinutesListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (keypadPanel.getVisibility() == View.INVISIBLE) {
                animSlidePanelUp(keypadPanel);
            }
        }
    };

    View.OnClickListener delayTimerSecondsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (keypadPanel.getVisibility() == View.INVISIBLE) {
                animSlidePanelUp(keypadPanel);
            }
        }
    };

    View.OnClickListener restTimerMinutesListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (keypadPanel.getVisibility() == View.INVISIBLE) {
                animSlidePanelUp(keypadPanel);
            }
        }
    };

    View.OnClickListener restTimerSecondsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (keypadPanel.getVisibility() == View.INVISIBLE) {
                animSlidePanelUp(keypadPanel);
            }
        }
    };


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
