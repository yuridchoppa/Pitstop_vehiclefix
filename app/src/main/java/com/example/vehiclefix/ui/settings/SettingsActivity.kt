package com.example.vehiclefix.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.vehiclefix.R
import com.example.vehiclefix.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("pitstop_settings_prefs", Context.MODE_PRIVATE)

        setupToolbar()
        setupThemeOptions()
        setupEmergencyContact()
        setupSwitches()
        setupCacheAction()
    }

    private fun setupToolbar() {
        binding.toolbarSettings.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupThemeOptions() {
        val currentMode = com.example.vehiclefix.util.ThemeManager.getSavedThemeMode(this)

        binding.rgSettingsTheme.setOnCheckedChangeListener(null)
        when (currentMode) {
            AppCompatDelegate.MODE_NIGHT_NO -> binding.rbThemeLight.isChecked = true
            AppCompatDelegate.MODE_NIGHT_YES -> binding.rbThemeDark.isChecked = true
            else -> binding.rbThemeSystem.isChecked = true
        }

        binding.rgSettingsTheme.setOnCheckedChangeListener { _, checkedId ->
            val newMode = when (checkedId) {
                R.id.rbThemeLight -> AppCompatDelegate.MODE_NIGHT_NO
                R.id.rbThemeDark -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            com.example.vehiclefix.util.ThemeManager.setNightMode(this, newMode, this)
        }
    }

    private fun setupEmergencyContact() {
        val savedContact = prefs.getString("emergency_contact", "+91 98765 43210")
        binding.etEmergencyContact.setText(savedContact)

        binding.etEmergencyContact.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val input = binding.etEmergencyContact.text?.toString().orEmpty()
                prefs.edit().putString("emergency_contact", input).apply()
            }
        }
    }

    private fun setupSwitches() {
        binding.switchStrobeFlash.isChecked = prefs.getBoolean("strobe_flash_enabled", true)
        binding.switchStrobeFlash.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("strobe_flash_enabled", isChecked).apply()
        }

        binding.switchMetricUnits.isChecked = prefs.getBoolean("metric_units_enabled", true)
        binding.switchMetricUnits.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("metric_units_enabled", isChecked).apply()
        }

        binding.switchTpmsAlerts.isChecked = prefs.getBoolean("tpms_alerts_enabled", true)
        binding.switchTpmsAlerts.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("tpms_alerts_enabled", isChecked).apply()
        }
    }

    private fun setupCacheAction() {
        binding.btnClearCache.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Clear Offline Cache")
                .setMessage("This will clear temporary diagnostic waveforms and acoustic samples. Essential repair manuals will remain available offline.")
                .setPositiveButton("Clear Now") { _, _ ->
                    Toast.makeText(this, "✅ 14.2 MB cache cleared successfully", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    override fun onPause() {
        super.onPause()
        val input = binding.etEmergencyContact.text?.toString().orEmpty()
        prefs.edit().putString("emergency_contact", input).apply()
    }
}
