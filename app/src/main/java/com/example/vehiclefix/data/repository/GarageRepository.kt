package com.example.vehiclefix.data.repository

import com.example.vehiclefix.data.models.Garage
import com.example.vehiclefix.data.models.GarageSpecialty
import com.example.vehiclefix.data.models.VehicleType

object GarageRepository {

    private val garages: List<Garage> = listOf(
        Garage(
            id = "g1",
            name = "Apex 24/7 Auto Rescue & Towing Hub",
            rating = 4.9,
            reviewCount = 342,
            address = "Plot 14, Main Bypass Highway, Near Ring Road Exit",
            distanceKm = 0.8,
            phone = "+919876543210",
            whatsappNumber = "+919876543210",
            is24x7 = true,
            openHours = "Open 24 Hours (Roadside Assistance & Towing)",
            specialties = listOf(GarageSpecialty.TOWING_RESCUE, GarageSpecialty.CAR, GarageSpecialty.THREE_WHEELER, GarageSpecialty.TWO_WHEELER),
            towTruckAvailable = true,
            estimatedArrivalMins = 12,
            pricingEstimate = "Towing from ₹600 ($7.50) • Spot Diagnosis ₹250",
            servicesOffered = listOf("Hydraulic Flatbed Towing", "Battery Jumpstart", "Spot Fuel Delivery", "On-Highway Mechanic Dispatch", "Bike & Car Roadside Rescue"),
            latitude = 28.6139,
            longitude = 77.2090
        ),

        Garage(
            id = "g2",
            name = "Speedy Rickshaw & 3-Wheeler Works",
            rating = 4.8,
            reviewCount = 219,
            address = "Shop 4, Auto Nagar, Industrial Road",
            distanceKm = 1.4,
            phone = "+919811223344",
            whatsappNumber = "+919811223344",
            is24x7 = false,
            openHours = "7:00 AM - 10:00 PM (Daily)",
            specialties = listOf(GarageSpecialty.THREE_WHEELER, GarageSpecialty.EV_BATTERY),
            towTruckAvailable = false,
            estimatedArrivalMins = 20,
            pricingEstimate = "Clutch wire fix ₹150 • Carburetor service ₹350",
            servicesOffered = listOf("Bajaj/TVS/Piaggio Specialists", "E-Rickshaw 48V Controller/Motor Repair", "Clutch Cable & Brake Shoe Overhaul", "Carburetor Jet Cleaning"),
            latitude = 28.6180,
            longitude = 77.2150
        ),

        Garage(
            id = "g3",
            name = "National Multi-Brand Car Care & Diagnostics",
            rating = 4.7,
            reviewCount = 485,
            address = "Sector 8 Market, Behind Petrol Pump",
            distanceKm = 2.1,
            phone = "+919833445566",
            whatsappNumber = "+919833445566",
            is24x7 = false,
            openHours = "8:30 AM - 9:00 PM",
            specialties = listOf(GarageSpecialty.CAR, GarageSpecialty.EV_BATTERY),
            towTruckAvailable = true,
            estimatedArrivalMins = 25,
            pricingEstimate = "OBD2 Computer Scan ₹400 • Engine Service from ₹1,200",
            servicesOffered = listOf("OBD-II Diagnostic Scanner", "Brake & ABS Fluid Bleeding", "Cooling System & Radiator Flush", "AC & Alternator Overhaul"),
            latitude = 28.6250,
            longitude = 77.2020
        ),

        Garage(
            id = "g4",
            name = "Highway Express Puncture & Radial Tire Care",
            rating = 4.8,
            reviewCount = 178,
            address = "Highway Service Road, Opp. Transport Nagar",
            distanceKm = 0.5,
            phone = "+919855667788",
            whatsappNumber = "+919855667788",
            is24x7 = true,
            openHours = "Open 24 Hours",
            specialties = listOf(GarageSpecialty.TIRE_PUNCTURE, GarageSpecialty.TWO_WHEELER, GarageSpecialty.CAR, GarageSpecialty.THREE_WHEELER),
            towTruckAvailable = false,
            estimatedArrivalMins = 10,
            pricingEstimate = "Bike puncture ₹60 • Car puncture plug ₹80",
            servicesOffered = listOf("Motorbike & Scooter Puncture Repair", "Tubeless & Tube Vulcanizing", "Mobile Tire Van (On-Spot)", "Nitrogen Air Top-Up"),
            latitude = 28.6110,
            longitude = 77.2130
        ),

        Garage(
            id = "g5",
            name = "VoltDrive E-Mobility & Battery Repair Center",
            rating = 4.9,
            reviewCount = 132,
            address = "EV Hub, Gate No. 2, Green Tech Park",
            distanceKm = 3.2,
            phone = "+919877889900",
            whatsappNumber = "+919877889900",
            is24x7 = false,
            openHours = "9:00 AM - 8:00 PM",
            specialties = listOf(GarageSpecialty.EV_BATTERY, GarageSpecialty.TWO_WHEELER, GarageSpecialty.THREE_WHEELER, GarageSpecialty.CAR),
            towTruckAvailable = true,
            estimatedArrivalMins = 30,
            pricingEstimate = "Battery Health Scan ₹300 • Controller Diagnostic ₹500",
            servicesOffered = listOf("Lithium & Lead Acid Battery Diagnostics", "BLDC Motor & Hall Sensor Rewinding", "EV Scooter & E-Rickshaw Repair", "High Voltage Safety Certified"),
            latitude = 28.6300,
            longitude = 77.1950
        ),

        Garage(
            id = "g6",
            name = "MotoDoc 2-Wheeler & Superbike Pitstop",
            rating = 4.9,
            reviewCount = 412,
            address = "Highway Service Lane, Near Indian Oil Petrol Bunk",
            distanceKm = 0.9,
            phone = "+919822334455",
            whatsappNumber = "+919822334455",
            is24x7 = true,
            openHours = "Open 24 Hours (Bike Rescue & Spot Repair)",
            specialties = listOf(GarageSpecialty.TWO_WHEELER, GarageSpecialty.TOWING_RESCUE, GarageSpecialty.TIRE_PUNCTURE),
            towTruckAvailable = true,
            estimatedArrivalMins = 15,
            pricingEstimate = "Chain adjust ₹80 • Spark plug replacement ₹100 • Spot breakdown ₹200",
            servicesOffered = listOf("Hero/Honda/Bajaj/Royal Enfield Specialists", "Drive Chain & Sprocket Repair", "Clutch & Throttle Cable Replacement", "Carburetor Tuning & Valve Clearence", "24/7 Motorbike Highway Breakdown Assistance"),
            latitude = 28.6160,
            longitude = 77.2070
        ),

        Garage(
            id = "g7",
            name = "EcoRide EV 2-Wheeler Tech Clinic",
            rating = 4.8,
            reviewCount = 189,
            address = "Shop 12, Main Market Road, Near Metro Pillar 184",
            distanceKm = 1.8,
            phone = "+919866778899",
            whatsappNumber = "+919866778899",
            is24x7 = false,
            openHours = "8:00 AM - 9:30 PM",
            specialties = listOf(GarageSpecialty.TWO_WHEELER, GarageSpecialty.EV_BATTERY),
            towTruckAvailable = false,
            estimatedArrivalMins = 20,
            pricingEstimate = "Side stand sensor fix ₹150 • EV Software/Throttle Scan ₹250",
            servicesOffered = listOf("Ola / Ather / TVS iQube / Chetak Experts", "Magnetic Side-Stand Sensor Diagnostics", "Throttle Cable & Hall Sensor Replacement", "BMS Battery Diagnostics & Charging Hub"),
            latitude = 28.6210,
            longitude = 77.2180
        )
    )

    fun getAllGarages(): List<Garage> = garages.sortedBy { it.distanceKm }

    fun getGaragesBySpecialty(specialty: GarageSpecialty): List<Garage> {
        if (specialty == GarageSpecialty.ALL) return getAllGarages()
        return garages.filter { it.specialties.contains(specialty) }.sortedBy { it.distanceKm }
    }

    fun getGaragesForVehicleType(vehicleType: VehicleType): List<Garage> {
        return when (vehicleType) {
            VehicleType.THREE_WHEELER -> garages.filter {
                it.specialties.contains(GarageSpecialty.THREE_WHEELER) || it.specialties.contains(GarageSpecialty.TOWING_RESCUE)
            }.sortedBy { it.distanceKm }
            VehicleType.CAR -> garages.filter {
                it.specialties.contains(GarageSpecialty.CAR) || it.specialties.contains(GarageSpecialty.TOWING_RESCUE)
            }.sortedBy { it.distanceKm }
            VehicleType.TWO_WHEELER -> garages.filter {
                it.specialties.contains(GarageSpecialty.TWO_WHEELER) || it.specialties.contains(GarageSpecialty.TOWING_RESCUE)
            }.sortedBy { it.distanceKm }
            else -> getAllGarages()
        }
    }

    fun searchGarages(query: String): List<Garage> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return getAllGarages()

        return garages.filter { garage ->
            garage.name.lowercase().contains(q) ||
            garage.address.lowercase().contains(q) ||
            garage.servicesOffered.any { it.lowercase().contains(q) } ||
            garage.specialties.any { it.displayName.lowercase().contains(q) }
        }.sortedBy { it.distanceKm }
    }

    fun getNearestEmergencyRescue(): Garage? = garages.find { it.is24x7 && it.towTruckAvailable }
}
