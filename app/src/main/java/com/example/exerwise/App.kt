package com.example.exerwise

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.exerwise.presentation.activity.MainActivity.Companion.DARK_THEME_ENABLED_KEY
import com.example.exerwise.presentation.activity.MainActivity.Companion.THEME_PREFERENCES

class App : Application() {


    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences(THEME_PREFERENCES, Context.MODE_PRIVATE)
        val isDarkThemeEnabled = sharedPreferences.getBoolean(DARK_THEME_ENABLED_KEY, false)

        applyTheme(isDarkThemeEnabled)
    }

    fun applyTheme(isDarkThemeEnabled: Boolean) {
        val mode = if (isDarkThemeEnabled) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }


}