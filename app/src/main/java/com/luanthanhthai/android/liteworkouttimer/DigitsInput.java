package com.luanthanhthai.android.liteworkouttimer;

/**
 * Created by Thai on 26.03.2017.
 * Copyright (c) [2016] [Luan Thanh Thai]
 * See the file LICENSE.txt for copying permission
 */

public class DigitsInput<T> {
    private T t;
    private int digits;

    public DigitsInput() {
        this.t = null;
        this.digits = 0;
    }

    public DigitsInput(T t, int digits) {
        this.t = t;
        this.digits = digits;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public int getDigits() {
        return digits;
    }

    public void setDigits(int digits) {
        if (!checkFirstDigitZero(this.digits)) {
            this.digits = digits;
        } else {
            this.digits = checkValidValue(concatenateDigits(this.digits, digits));
        }
    }

    /**
     * Check if time is less then 10
     */
    public boolean checkFirstDigitZero(int time) {
        return time < 10;
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
}
