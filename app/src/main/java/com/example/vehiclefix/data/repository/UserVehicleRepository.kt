package com.example.vehiclefix.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.vehiclefix.data.models.VehicleProfile
import com.example.vehiclefix.data.models.VehicleType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserVehicleRepository(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("vehiclefix_garage_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    private val defaultVehicles = listOf(
        VehicleProfile(
            id = "v_default_1",
            name = "My City Car",
            vehicleType = VehicleType.CAR,
            make = "Hyundai",
            model = "i20 Asta",
            year = 2022,
            fuelType = "Petrol",
            registrationNumber = "DL-01-AB-1234",
            odometerKm = 34500,
            isDefault = true
        ),
        VehicleProfile(
            id = "v_default_2",
            name = "Commercial Auto",
            vehicleType = VehicleType.THREE_WHEELER,
            make = "Bajaj",
            model = "Compact 4S LPG",
            year = 2021,
            fuelType = "LPG / Petrol",
            registrationNumber = "DL-1R-XY-9876",
            odometerKm = 52100,
            isDefault = false
        ),
        VehicleProfile(
            id = "v_default_3",
            name = "Commuter Motorbike",
            vehicleType = VehicleType.TWO_WHEELER,
            make = "Hero",
            model = "Splendor Plus BS6",
            year = 2023,
            fuelType = "Petrol",
            registrationNumber = "DL-04-MK-5678",
            odometerKm = 14200,
            isDefault = false
        )
    )

    fun getVehicles(): List<VehicleProfile> {
        val json = prefs.getString("saved_vehicles", null)
        if (json.isNullOrEmpty()) {
            saveVehicles(defaultVehicles)
            return defaultVehicles
        }
        val type = object : TypeToken<List<VehicleProfile>>() {}.type
        return try {
            gson.fromJson(json, type) ?: defaultVehicles
        } catch (e: Exception) {
            defaultVehicles
        }
    }

    fun saveVehicles(vehicles: List<VehicleProfile>) {
        val json = gson.toJson(vehicles)
        prefs.edit().putString("saved_vehicles", json).apply()
    }

    fun addVehicle(vehicle: VehicleProfile) {
        val current = getVehicles().toMutableList()
        current.add(vehicle)
        saveVehicles(current)
    }

    fun deleteVehicle(id: String) {
        val current = getVehicles().filterNot { it.id == id }
        saveVehicles(current)
    }

    fun getActiveVehicle(): VehicleProfile {
        return getVehicles().find { it.isDefault } ?: getVehicles().firstOrNull() ?: defaultVehicles.first()
    }

    fun setDefaultVehicle(id: String) {
        val updated = getVehicles().map {
            it.copy(isDefault = (it.id == id))
        }
        saveVehicles(updated)
    }
}
