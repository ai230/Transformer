package com.aiyamamoto.transforemerapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by aiyamamoto on 2018-09-14.
 */

public class SharedPreferencesUtils {

    public static final String SHARED_PREF_TRANSFORMER = "shared_pref_transformer";
    public static final String SHARED_PREF_TOKEN = "shared_pref_token";

    public static void setToken(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_TRANSFORMER, Context.MODE_PRIVATE);
        prefs.edit().putString(SHARED_PREF_TOKEN, token).apply();
    }

    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_TRANSFORMER, Context.MODE_PRIVATE);
        return prefs.getString(SHARED_PREF_TOKEN, "");
    }

    public static void clearToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_TRANSFORMER, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}
