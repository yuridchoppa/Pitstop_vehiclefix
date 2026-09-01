package com.example.vehiclefix.data.repository

import com.example.vehiclefix.data.models.VehicleType

data class LibraryVehicle(
    val id: String,
    val make: String,
    val model: String,
    val vehicleType: VehicleType,
    val category: String,
    val fuelType: String,
    val engineDisplacement: String,
    val powerBhp: String,
    val oilGrade: String,
    val tirePressurePsi: String,
    val sparkPlugType: String,
    val serviceIntervalKm: String,
    val commonIssues: List<String>,
    val emoji: String
)

object VehicleLibraryRepository {

    val vehicleLibrary: List<LibraryVehicle> = listOf(
        // CARS
        LibraryVehicle(
            id = "lib_car_i20",
            make = "Hyundai",
            model = "i20 Asta 1.2 Kappa",
            vehicleType = VehicleType.CAR,
            category = "Premium Hatchback",
            fuelType = "Petrol",
            engineDisplacement = "1197 cc (4-Cylinder)",
            powerBhp = "82 bhp @ 6,000 rpm",
            oilGrade = "5W-30 API SN Full Synthetic (3.6 L)",
            tirePressurePsi = "33 PSI Front / 33 PSI Rear",
            sparkPlugType = "NGK SILZKR6B11 Iridium (0.9 mm gap)",
            serviceIntervalKm = "10,000 km / 12 Months",
            commonIssues = listOf("Ignition coil pack misfire at 40k km", "Front stabilizer bushing rattle", "AC evaporator dust buildup"),
            emoji = "🚗"
        ),
        LibraryVehicle(
            id = "lib_car_nexon_ev",
            make = "Tata",
            model = "Nexon EV Max / Long Range",
            vehicleType = VehicleType.CAR,
            category = "Electric SUV",
            fuelType = "Electric (40.5 kWh)",
            engineDisplacement = "Permanent Magnet AC Motor",
            powerBhp = "141 bhp / 250 Nm Torque",
            oilGrade = "Castrol Transmax EV Reducer Fluid (0.8 L)",
            tirePressurePsi = "34 PSI Front / 34 PSI Rear",
            sparkPlugType = "N/A (Pure Electric Powertrain)",
            serviceIntervalKm = "15,000 km / 12 Months",
            commonIssues = listOf("12V auxiliary battery discharge if idle > 3 weeks", "Regen braking sensor calibration", "Charging flap actuator freeze"),
            emoji = "⚡"
        ),
        LibraryVehicle(
            id = "lib_car_swift",
            make = "Maruti Suzuki",
            model = "Swift ZXi 1.2 DualJet",
            vehicleType = VehicleType.CAR,
            category = "Compact Hatchback",
            fuelType = "Petrol",
            engineDisplacement = "1197 cc Dual VVT",
            powerBhp = "89 bhp @ 6,000 rpm",
            oilGrade = "0W-16 / 0W-20 Ultra-Low Viscosity (3.1 L)",
            tirePressurePsi = "32 PSI Front / 29 PSI Rear",
            sparkPlugType = "NGK KR6A-10 (1.0 mm gap)",
            serviceIntervalKm = "10,000 km / 12 Months",
            commonIssues = listOf("Clutch shudder in slow traffic", "Brake pad glaze in city stop-and-go", "Idling stop-start sensor delay"),
            emoji = "🚗"
        ),
        LibraryVehicle(
            id = "lib_car_thar",
            make = "Mahindra",
            model = "Thar 4x4 2.2 mHawk",
            vehicleType = VehicleType.CAR,
            category = "Off-Road 4WD SUV",
            fuelType = "Diesel",
            engineDisplacement = "2184 cc CRDe Turbo",
            powerBhp = "130 bhp @ 3,750 rpm",
            oilGrade = "5W-30 Maximile DPF Synthetic (6.0 L)",
            tirePressurePsi = "32 PSI Front / 32 PSI Rear",
            sparkPlugType = "Bosch Glow Plugs (Diesel Compression)",
            serviceIntervalKm = "10,000 km / 12 Months",
            commonIssues = listOf("DPF soot clogging with short city trips", "Front axle disconnect actuator dirt ingress", "Transfer case fluid moisture"),
            emoji = "🚙"
        ),

        // 2-WHEELERS (MOTORBIKES & SCOOTERS)
        LibraryVehicle(
            id = "lib_bike_splendor",
            make = "Hero",
            model = "Splendor Plus BS6 FI",
            vehicleType = VehicleType.TWO_WHEELER,
            category = "Commuter Motorcycle",
            fuelType = "Petrol",
            engineDisplacement = "97.2 cc Single Cylinder OHC",
            powerBhp = "7.9 bhp @ 8,000 rpm",
            oilGrade = "10W-30 4T JASO MA2 (0.9 L)",
            tirePressurePsi = "28 PSI Front / 32 PSI Rear (36 with Pillion)",
            sparkPlugType = "NGK CPR6EA-9 (0.8 - 0.9 mm gap)",
            serviceIntervalKm = "3,000 km / 6 Months",
            commonIssues = listOf("Drive chain slack / derailment if unlubricated", "Fouled spark plug causing kick-start failure", "Carb/injector clogged with stale fuel"),
            emoji = "🏍️"
        ),
        LibraryVehicle(
            id = "lib_bike_activa",
            make = "Honda",
            model = "Activa 6G PGM-FI",
            vehicleType = VehicleType.TWO_WHEELER,
            category = "Automatic Scooter",
            fuelType = "Petrol",
            engineDisplacement = "109.5 cc eSP Fan-Cooled",
            powerBhp = "7.7 bhp @ 8,000 rpm",
            oilGrade = "10W-30 4T MB Scooter Oil (0.75 L)",
            tirePressurePsi = "29 PSI Front / 36 PSI Rear",
            sparkPlugType = "NGK MR7C-9N (0.85 mm gap)",
            serviceIntervalKm = "3,500 km / 6 Months",
            commonIssues = listOf("CVT drive belt glazing / slipping under load", "Roller weight flat spots causing shudder", "Throttle cable friction"),
            emoji = "🛵"
        ),
        LibraryVehicle(
            id = "lib_bike_classic350",
            make = "Royal Enfield",
            model = "Classic 350 J-Series",
            vehicleType = VehicleType.TWO_WHEELER,
            category = "Cruiser / Retro Motorcycle",
            fuelType = "Petrol",
            engineDisplacement = "349 cc Air-Oil Cooled SOHC",
            powerBhp = "20.2 bhp @ 6,100 rpm",
            oilGrade = "15W-50 Semi-Synthetic JASO MA2 (2.1 L)",
            tirePressurePsi = "32 PSI Front / 36 PSI Rear",
            sparkPlugType = "Bosch YR7DES (0.8 mm gap)",
            serviceIntervalKm = "5,000 km / 6 Months",
            commonIssues = listOf("Hydraulic tappet valve noise when oil is low", "Clutch cable stretch in heavy traffic", "Fuel pump prime relay contact oxidization"),
            emoji = "🏍️"
        ),
        LibraryVehicle(
            id = "lib_bike_ola_s1",
            make = "Ola Electric",
            model = "S1 Pro Gen 2",
            vehicleType = VehicleType.TWO_WHEELER,
            category = "Electric Smart Scooter",
            fuelType = "Electric (4.0 kWh)",
            engineDisplacement = "11 kW Peak Mid-Drive PMSM",
            powerBhp = "14.7 bhp Peak Power",
            oilGrade = "N/A (Direct Belt/Planetary Reducer)",
            tirePressurePsi = "33 PSI Front / 33 PSI Rear",
            sparkPlugType = "N/A (Pure Electric)",
            serviceIntervalKm = "10,000 km / 12 Months",
            commonIssues = listOf("Side-stand magnetic cutoff sensor misalignment", "Belt tension calibration", "Screen MCU reboot in extreme heat"),
            emoji = "⚡"
        ),

        // 3-WHEELERS (COMMERCIAL & AUTOS)
        LibraryVehicle(
            id = "lib_auto_bajaj_re",
            make = "Bajaj",
            model = "RE Compact 4S LPG / Petrol",
            vehicleType = VehicleType.THREE_WHEELER,
            category = "Passenger Auto-Rickshaw",
            fuelType = "LPG / Petrol",
            engineDisplacement = "236.2 cc 4-Stroke DTS-i",
            powerBhp = "10.0 bhp @ 4,750 rpm",
            oilGrade = "20W-50 4T JASO MA2 Heavy Duty (1.4 L)",
            tirePressurePsi = "24 PSI Front / 36 PSI Rear",
            sparkPlugType = "Champion PRG6HCC / Bosch Dual Plug",
            serviceIntervalKm = "4,000 km / 4 Months",
            commonIssues = listOf("Clutch cable snapping due to frequent city shifting", "LPG vaporizer diaphragm freeze in cold mornings", "Brake drum adjuster shoe uneven wear"),
            emoji = "🛺"
        ),
        LibraryVehicle(
            id = "lib_auto_piaggio_ape",
            make = "Piaggio",
            model = "Ape Auto DX Diesel",
            vehicleType = VehicleType.THREE_WHEELER,
            category = "Passenger Auto-Rickshaw",
            fuelType = "Diesel",
            engineDisplacement = "599 cc Single Cylinder Direct Injection",
            powerBhp = "9.4 bhp @ 3,600 rpm",
            oilGrade = "15W-40 CI-4 Diesel Engine Oil (1.75 L)",
            tirePressurePsi = "26 PSI Front / 38 PSI Rear",
            sparkPlugType = "Glow Plug Pre-Heater",
            serviceIntervalKm = "5,000 km / 6 Months",
            commonIssues = listOf("Diesel fuel filter air locking after tank dries", "Front fork helical spring sag", "Alternator belt squeal under load"),
            emoji = "🛺"
        )
    )

    fun getAllVehicles(): List<LibraryVehicle> = vehicleLibrary

    fun searchVehicles(query: String, type: VehicleType = VehicleType.ALL): List<LibraryVehicle> {
        val q = query.trim().lowercase()
        return vehicleLibrary.filter { v ->
            val matchesType = type == VehicleType.ALL || v.vehicleType == type
            val matchesQuery = q.isEmpty() ||
                    v.make.lowercase().contains(q) ||
                    v.model.lowercase().contains(q) ||
                    v.category.lowercase().contains(q) ||
                    v.fuelType.lowercase().contains(q)
            matchesType && matchesQuery
        }
    }

    fun getVehicleById(id: String): LibraryVehicle? {
        return vehicleLibrary.find { it.id == id }
    }
}
