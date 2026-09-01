package com.example.vehiclefix.util

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {

    private const val PREFS_NAME = "pitstop_theme_prefs"
    private const val KEY_THEME_MODE = "theme_mode"

    fun getSavedThemeMode(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    fun applySavedTheme(context: Context) {
        val savedMode = getSavedThemeMode(context)
        if (AppCompatDelegate.getDefaultNightMode() != savedMode) {
            AppCompatDelegate.setDefaultNightMode(savedMode)
        }
    }

    fun setNightMode(context: Context, mode: Int, activity: Activity? = null) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val currentSaved = prefs.getInt(KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        val currentDefault = AppCompatDelegate.getDefaultNightMode()

        if (currentSaved == mode && currentDefault == mode) {
            return
        }

        prefs.edit().putInt(KEY_THEME_MODE, mode).apply()

        activity?.let { act ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                act.overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, 0, 0)
                act.overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE, 0, 0)
            } else {
                @Suppress("DEPRECATION")
                act.overridePendingTransition(0, 0)
            }
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }
}
