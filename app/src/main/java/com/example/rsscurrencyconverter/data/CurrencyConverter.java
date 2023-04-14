package com.example.rsscurrencyconverter.data;

import android.icu.lang.UCharacter;

public class CurrencyConverter {
    public static String convert(String srcValue, double srcRate, String destValue, double destRate){
        double sV = Double.parseDouble(srcValue);
        double dV = Double.parseDouble(destValue);

        double res = sV*srcRate/destRate;
        return String.valueOf(res);
    }
}
