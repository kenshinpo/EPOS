package com.pochih.mokapos.utility;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by A-Po on 2018/01/04.
 */

public class InputFilterQuantity implements InputFilter {

    private int min, max;

    public InputFilterQuantity(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public InputFilterQuantity(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) {
        }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}