package com.ravemaster.recipeapp;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.ravemaster.recipeapp.utilities.Constants;
import com.ravemaster.recipeapp.utilities.PreferenceManager;

public class MyApp extends Application {
    PreferenceManager preferenceManager;
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
