package com.example.appwidget

import android.app.Application
import android.util.Log
import androidx.core.content.edit

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences(Constant.PREF_NAME, MODE_PRIVATE)
        if(sharedPreferences.getBoolean(Constant.IS_FIRST_RUN, true)){
            sharedPreferences.edit { putBoolean(Constant.IS_FIRST_RUN, false) }
            sharedPreferences.edit { putInt(Constant.APP_COUNT, 0) }
            sharedPreferences.edit { putLong(Constant.TIME, System.currentTimeMillis()) }
        }
        Log.d("MyApplication", "onCreate: hyangly")
    }
}