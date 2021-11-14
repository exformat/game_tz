package com.exformat.tz;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_LINK = "link";
    public static final String DEFAULT_VALUE = "default";


    public static void saveData(Context context,String key, String text) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, text);
        editor.apply();
    }

    public static String loadData(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, DEFAULT_VALUE);
    }
}
