package com.luanthanhthai.android.liteworkouttimer;

/**
 * Created by Thai on 26.03.2017.
 * Copyright (c) [2016] [Luan Thanh Thai]
 * See the file LICENSE.txt for copying permission
 */

class DigitsInput<T> {
    private T view;
    private int digits;

    DigitsInput(T view, int digits) {
        this.view = view;
        this.digits = digits;
    }

    public T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
    }

    int getDigits() {
        return digits;
    }

    void setDigits(int digits) {
        if (!checkFirstDigitZero(this.digits)) {
            this.digits = digits;
        } else {
            this.digits = checkValidValue(concatenateDigits(this.digits, digits));
        }
    }

    /**
     * Check if time is less then 10
     */
    private boolean checkFirstDigitZero(int time) {
        return time < 10;
    }

    /**
     * Concatenate the first digit with the second digit
     */
    private int concatenateDigits(int second, int first) {
        return (second * 10) + first;
    }

    /**
     * Check if user inputted value is less than 60
     */
    private int checkValidValue(int userInput) {
        if (userInput < 60) {
            return userInput;
        } else {
            return 59;
        }
    }
}
