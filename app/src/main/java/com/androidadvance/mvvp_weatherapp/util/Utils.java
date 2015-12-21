package com.androidadvance.mvvp_weatherapp.util;

public class Utils {

    public static double celsius_to_kelvin(double celsius) {
        return celsius + 273.15;
    }

    public static double fahrenheit_to_kelvin(double fahrenheit) {
        return (fahrenheit - 32) * (5 / 9) + 273.15;
    }

}
