package com.example.androidassignment1.DataAccess;

import android.content.SharedPreferences;

public class ItemDAFactory {
    public static synchronized iItemDA getInstance(SharedPreferences sharedPreferences) {
        return new ItemDA(sharedPreferences);
    }
}
