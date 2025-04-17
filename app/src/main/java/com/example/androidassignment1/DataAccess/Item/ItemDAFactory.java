package com.example.androidassignment1.DataAccess.Item;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.androidassignment1.R;

public class ItemDAFactory {
    public static synchronized iItemDA getInstance(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.prefs_file), Context.MODE_PRIVATE);
        return new ItemDA(prefs);
    }
}
