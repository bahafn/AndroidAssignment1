package com.example.androidassignment1.DataAccess.Cart;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.androidassignment1.R;

public class CartDAFactory {
    public static synchronized iCartDA getInstance(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.prefs_file), Context.MODE_PRIVATE);
        return new CartDA(prefs);
    }
}
