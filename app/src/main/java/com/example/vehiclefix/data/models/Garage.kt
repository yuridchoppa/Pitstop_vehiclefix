package com.example.vehiclefix.data.models

enum class GarageSpecialty(val displayName: String, val emoji: String) {
    ALL("All Services", "🔧"),
    CAR("Car & SUV Care", "🚗"),
    THREE_WHEELER("3-Wheeler & Auto Specialist", "🛺"),
    TWO_WHEELER("Bike & Scooter Repair", "🏍️"),
    TOWING_RESCUE("24/7 Towing & Rescue", "🚨"),
    EV_BATTERY("EV & Battery Charging", "⚡"),
    TIRE_PUNCTURE("Tire & Puncture Fix", "🛞")
}

data class Garage(
    val id: String,
    val name: String,
    val rating: Double,
    val reviewCount: Int,
    val address: String,
    val distanceKm: Double,
    val phone: String,
    val whatsappNumber: String,
    val is24x7: Boolean,
    val openHours: String,
    val specialties: List<GarageSpecialty>,
    val towTruckAvailable: Boolean,
    val estimatedArrivalMins: Int,
    val pricingEstimate: String,
    val servicesOffered: List<String>,
    val latitude: Double,
    val longitude: Double
) {
    fun getWhatsAppUri(prefilledMessage: String): String {
        val cleanNumber = whatsappNumber.replace("+", "").replace(" ", "").replace("-", "")
        return "https://api.whatsapp.com/send?phone=$cleanNumber&text=${java.net.URLEncoder.encode(prefilledMessage, "UTF-8")}"
    }

    fun getDialUri(): String = "tel:$phone"

    fun getMapsUri(): String = "geo:$latitude,$longitude?q=${java.net.URLEncoder.encode(name + ", " + address, "UTF-8")}"
}

data class VehicleProfile(
    val id: String,
    val name: String,
    val vehicleType: VehicleType,
    val make: String,
    val model: String,
    val year: Int,
    val fuelType: String,
    val registrationNumber: String,
    val odometerKm: Int,
    val isDefault: Boolean = false
)
