package com.example.vehiclefix

import android.app.Application
import com.example.vehiclefix.util.ThemeManager

class VehicleFixApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Apply user-selected theme immediately at process initialization before any activity renders
        ThemeManager.applySavedTheme(this)
    }
}
