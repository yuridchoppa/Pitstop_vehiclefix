package com.example.vehiclefix.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

enum class ServiceStatus(val label: String, val stepIndex: Int, val badgeColor: String) {
    BOOKED("Confirmed & Scheduled", 1, "#00B4D8"),
    INSPECTION("Vehicle In-Inspection", 2, "#FFB703"),
    IN_PROGRESS("Service In-Progress", 3, "#FB8500"),
    READY("Ready for Pickup / Handover", 4, "#00E676"),
    COMPLETED("Service Completed", 5, "#64748B")
}

data class ServicePackage(
    val id: String,
    val title: String,
    val price: String,
    val duration: String,
    val emoji: String,
    val summary: String,
    val inclusions: List<String>,
    val isPopular: Boolean = false
)

data class ServiceBooking(
    val id: String,
    val vehicleName: String,
    val vehiclePlate: String,
    val packageTitle: String,
    val workshopName: String,
    val workshopAddress: String,
    val scheduledDate: String,
    val scheduledTime: String,
    val totalPrice: String,
    val status: ServiceStatus,
    val bookingTimestamp: Long = System.currentTimeMillis()
)

class ServiceBookingRepository(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("pitstop_service_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        val availablePackages = listOf(
            ServicePackage(
                id = "pkg_basic",
                title = "Basic Oil & Lube Service",
                price = "₹1,499 ($18)",
                duration = "45 mins",
                emoji = "🛢️",
                summary = "Essential engine lubrication and 15-point safety inspection.",
                inclusions = listOf("Full Synthetic 5W-30 / 10W-40 Engine Oil", "OEM Oil Filter Replacement", "Windshield Washer & Coolant Top-Up", "15-Point Chassis & Tire Health Check")
            ),
            ServicePackage(
                id = "pkg_periodic",
                title = "Periodic Standard Maintenance",
                price = "₹2,999 ($36)",
                duration = "1.5 hours",
                emoji = "🛠️",
                summary = "Comprehensive 35-point preventive service for peak engine life.",
                inclusions = listOf("Synthetic Engine Oil + Filter", "Engine Air Filter & Cabin AC Filter", "Brake Pad & Rotor Thickness Check", "Coolant & Brake Fluid Moisture Analysis", "35-Point Digital Health Report"),
                isPopular = true
            ),
            ServicePackage(
                id = "pkg_major",
                title = "Comprehensive Overhaul & Brake",
                price = "₹4,899 ($59)",
                duration = "3 hours",
                emoji = "⚙️",
                summary = "Complete powertrain, brake bleeding, and AC sanitization overhaul.",
                inclusions = listOf("Complete Periodic Service Inclusions", "DOT 4 Hydraulic Brake Fluid Flush", "Throttle Body & Fuel Injector Cleaning", "AC Evaporator Antimicrobial Cleaning", "OBD-II 60-Sensor Diagnostic Scan")
            ),
            ServicePackage(
                id = "pkg_battery_tire",
                title = "Spot Battery & Puncture Rescue",
                price = "₹350 ($4.20)",
                duration = "15 mins",
                emoji = "⚡",
                summary = "On-highway jumpstart, puncture plug, or replacement battery delivery.",
                inclusions = listOf("Instant Mobile Mechanic Dispatch", "12V Battery Health & Alternator Test", "On-Spot Tubeless Puncture Plug", "Digital Voltage Telemetry Scan")
            )
        )
    }

    fun getActiveBooking(): ServiceBooking? {
        val json = prefs.getString("active_booking", null) ?: return defaultActiveBooking()
        return try {
            gson.fromJson(json, ServiceBooking::class.java)
        } catch (_: Exception) {
            defaultActiveBooking()
        }
    }

    fun createBooking(booking: ServiceBooking) {
        val json = gson.toJson(booking)
        prefs.edit().putString("active_booking", json).apply()
    }

    fun clearActiveBooking() {
        prefs.edit().remove("active_booking").apply()
    }

    private fun defaultActiveBooking(): ServiceBooking {
        return ServiceBooking(
            id = "bk_78291",
            vehicleName = "Hyundai i20 Asta",
            vehiclePlate = "DL-01-AB-1234",
            packageTitle = "Periodic Standard Maintenance",
            workshopName = "Apex 24/7 Auto Rescue & Towing Hub",
            workshopAddress = "Plot 14, Main Bypass Highway",
            scheduledDate = "Today",
            scheduledTime = "3:30 PM",
            totalPrice = "₹2,999",
            status = ServiceStatus.IN_PROGRESS
        )
    }
}
