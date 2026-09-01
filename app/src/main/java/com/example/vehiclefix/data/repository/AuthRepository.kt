package com.example.vehiclefix.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val bio: String = "🚗 Daily Motorist & DIY Enthusiast • Hyundai i20 & Hero Splendor",
    val licenseNumber: String = "DL-042022007891",
    val membershipTier: String = "Gold Motorist ⚡",
    val emergencyContact: String = "+91 98765 43210",
    val joinedDate: String = "August 2026",
    val totalServicesBooked: Int = 12,
    val avatarEmoji: String = "👨‍✈️"
)

class AuthRepository(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("pitstop_auth_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_PROFILE = "user_profile"
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    fun getCurrentUser(): UserProfile? {
        val json = prefs.getString(KEY_USER_PROFILE, null) ?: return defaultUserProfile()
        return try {
            gson.fromJson(json, UserProfile::class.java) ?: defaultUserProfile()
        } catch (_: Exception) {
            defaultUserProfile()
        }
    }

    fun login(email: String, name: String? = null, phone: String? = null): UserProfile {
        val resolvedName = if (!name.isNullOrBlank()) name else email.substringBefore("@").replaceFirstChar { it.uppercase() }
        val user = UserProfile(
            id = "usr_" + System.currentTimeMillis(),
            name = resolvedName,
            email = email,
            phone = phone ?: "+91 98765 43210",
            bio = "🚗 Daily Motorist & Auto Enthusiast • Hyundai i20 & Hero Splendor",
            licenseNumber = "DL-042022007891",
            membershipTier = "Gold Motorist ⚡",
            emergencyContact = "+91 98765 43210"
        )
        saveProfile(user)
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, true).apply()
        return user
    }

    fun saveProfile(user: UserProfile) {
        prefs.edit()
            .putString(KEY_USER_PROFILE, gson.toJson(user))
            .apply()
    }

    fun logout() {
        prefs.edit()
            .putBoolean(KEY_IS_LOGGED_IN, false)
            .remove(KEY_USER_PROFILE)
            .apply()
    }

    private fun defaultUserProfile(): UserProfile {
        return UserProfile(
            id = "usr_alex_default",
            name = "Alex Driver",
            email = "alex.driver@pitstop.io",
            phone = "+91 98765 43210",
            bio = "🚗 Daily Motorist & Auto Enthusiast • Hyundai i20 & Hero Splendor",
            licenseNumber = "DL-042022007891",
            membershipTier = "Gold Motorist ⚡",
            emergencyContact = "+91 98765 43210",
            joinedDate = "Member since August 2026",
            totalServicesBooked = 12,
            avatarEmoji = "👨‍✈️"
        )
    }
}
