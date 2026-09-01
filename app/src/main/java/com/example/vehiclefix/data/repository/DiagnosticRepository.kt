package com.example.vehiclefix.data.repository

import com.example.vehiclefix.data.models.Severity
import com.example.vehiclefix.data.models.VehicleType

enum class ScanCategory {
    VISUAL,
    AUDIO,
    OBD2
}

data class ScanPreset(
    val id: String,
    val title: String,
    val category: ScanCategory,
    val vehicleType: VehicleType,
    val detectedIssueId: String,
    val confidence: Int,
    val description: String,
    val iconEmoji: String,
    val matchedSignals: List<String>
)

data class ObdCodeInfo(
    val code: String,
    val title: String,
    val system: String,
    val severity: Severity,
    val description: String,
    val matchedIssueId: String?
)

object DiagnosticRepository {

    val visualPresets: List<ScanPreset> = listOf(
        ScanPreset(
            id = "vis_check_engine",
            title = "Check Engine Light (MIL) Active",
            category = ScanCategory.VISUAL,
            vehicleType = VehicleType.CAR,
            detectedIssueId = "obd_p0300_misfire",
            confidence = 96,
            description = "Solid or flashing amber engine outline detected on instrument cluster.",
            iconEmoji = "⚠️",
            matchedSignals = listOf("Amber MIL light illuminated", "OBD error code logged in ECU", "Potential cylinder misfire")
        ),
        ScanPreset(
            id = "vis_flat_tire",
            title = "Deflated / Punctured Tire",
            category = ScanCategory.VISUAL,
            vehicleType = VehicleType.CAR,
            detectedIssueId = "car_flat_tire",
            confidence = 98,
            description = "Severe loss of tire profile height and sidewall rim contact detected.",
            iconEmoji = "🛞",
            matchedSignals = listOf("0-10 PSI tire pressure", "Rim resting on ground", "Tread nail/screw penetration")
        ),
        ScanPreset(
            id = "vis_coolant_steam",
            title = "Radiator Steam & Coolant Puddle",
            category = ScanCategory.VISUAL,
            vehicleType = VehicleType.CAR,
            detectedIssueId = "car_engine_overheating",
            confidence = 94,
            description = "White vapor issuing from front grille and sweet-smelling fluid beneath engine.",
            iconEmoji = "♨️",
            matchedSignals = listOf("Engine block temp > 105°C", "Green/Pink coolant pool", "Cooling fan inactive")
        ),
        ScanPreset(
            id = "vis_auto_clutch",
            title = "3-Wheeler Broken Clutch Wire",
            category = ScanCategory.VISUAL,
            vehicleType = VehicleType.THREE_WHEELER,
            detectedIssueId = "auto_clutch_cable",
            confidence = 97,
            description = "Frayed steel cable snapped near handlebar lever or engine clutch arm.",
            iconEmoji = "🛺",
            matchedSignals = listOf("Zero clutch lever tension", "Exposed broken steel strands", "Gears un-shiftable")
        ),
        ScanPreset(
            id = "vis_battery_corrosion",
            title = "Corroded Battery Terminals & Low Voltage",
            category = ScanCategory.VISUAL,
            vehicleType = VehicleType.CAR,
            detectedIssueId = "car_dead_battery",
            confidence = 95,
            description = "White/blue acid sulfate encrustation on lead terminals with <11.8V output.",
            iconEmoji = "🔋",
            matchedSignals = listOf("Heavy lead sulfate build-up", "High terminal contact resistance", "Rapid clicking on ignition")
        ),
        ScanPreset(
            id = "vis_bike_chain_slack",
            title = "Motorbike Loose / Slipped Drive Chain",
            category = ScanCategory.VISUAL,
            vehicleType = VehicleType.TWO_WHEELER,
            detectedIssueId = "bike_drive_chain",
            confidence = 98,
            description = "Severe chain sag (>35mm) or drive chain derailed from rear sprocket.",
            iconEmoji = "🏍️",
            matchedSignals = listOf("Drive chain derailed from sprocket teeth", "Heavy chain sag >35mm", "Scoring marks on swingarm")
        ),
        ScanPreset(
            id = "vis_bike_spark_plug",
            title = "2-Wheeler Sooty / Wet Spark Plug",
            category = ScanCategory.VISUAL,
            vehicleType = VehicleType.TWO_WHEELER,
            detectedIssueId = "bike_spark_starting_trouble",
            confidence = 96,
            description = "Heavy black carbon or petrol soak on electrode causing starting failure.",
            iconEmoji = "⚡",
            matchedSignals = listOf("Black sooty carbon crust", "Electrode gap bridged by carbon", "No spark jump on crank")
        )
    )

    val audioPresets: List<ScanPreset> = listOf(
        ScanPreset(
            id = "aud_engine_knock",
            title = "Heavy Engine Knocking / Rod Tap",
            category = ScanCategory.AUDIO,
            vehicleType = VehicleType.CAR,
            detectedIssueId = "obd_p0300_misfire",
            confidence = 91,
            description = "Low-frequency rhythmic metallic knocking that increases with engine RPM.",
            iconEmoji = "🔊",
            matchedSignals = listOf("Metallic impact sound @ 15-40 Hz", "Crankshaft bearing wear", "Severe cylinder misfire")
        ),
        ScanPreset(
            id = "aud_belt_squeal",
            title = "High-Pitched Alternator Belt Squeal",
            category = ScanCategory.AUDIO,
            vehicleType = VehicleType.CAR,
            detectedIssueId = "car_dead_battery",
            confidence = 94,
            description = "Loud screeching on startup or when accelerating due to slipping serpentine belt.",
            iconEmoji = "🔈",
            matchedSignals = listOf("High frequency screech @ 2-4 kHz", "Belt slippage under electrical load", "Alternator undercharging")
        ),
        ScanPreset(
            id = "aud_brake_grind",
            title = "Metallic Brake Grinding / Scraping",
            category = ScanCategory.AUDIO,
            vehicleType = VehicleType.CAR,
            detectedIssueId = "car_brake_spongy",
            confidence = 93,
            description = "Harsh metal-on-metal grinding noise when pressing brake pedal.",
            iconEmoji = "🛑",
            matchedSignals = listOf("Brake friction material worn to backing plate", "Rotor disc scoring hazard", "Air in brake line")
        ),
        ScanPreset(
            id = "aud_auto_backfire",
            title = "3-Wheeler Carburetor Popping / Backfire",
            category = ScanCategory.AUDIO,
            vehicleType = VehicleType.THREE_WHEELER,
            detectedIssueId = "auto_carb_overflow",
            confidence = 92,
            description = "Loud popping through carburetor or exhaust with fuel sputtering.",
            iconEmoji = "💥",
            matchedSignals = listOf("Rich unburnt fuel in exhaust header", "Float valve needle stuck open", "Wet fouled spark plug")
        ),
        ScanPreset(
            id = "aud_bike_chain_slap",
            title = "Motorbike Drive Chain Slap Noise",
            category = ScanCategory.AUDIO,
            vehicleType = VehicleType.TWO_WHEELER,
            detectedIssueId = "bike_drive_chain",
            confidence = 97,
            description = "Loud rhythmic metallic clatter of loose chain slapping against the swingarm.",
            iconEmoji = "🏍️",
            matchedSignals = listOf("Metallic clanking on acceleration", "Chain slapping swingarm guide", "Excessive chain slack")
        ),
        ScanPreset(
            id = "aud_bike_kick_misfire",
            title = "2-Wheeler Kick-Start Crank Without Ignition",
            category = ScanCategory.AUDIO,
            vehicleType = VehicleType.TWO_WHEELER,
            detectedIssueId = "bike_spark_starting_trouble",
            confidence = 93,
            description = "Dry cranking sound with repeated kick lever stroke but zero combustion firing.",
            iconEmoji = "💨",
            matchedSignals = listOf("Hollow cranking noise", "No combustion stroke exhaust puff", "Fouled spark electrode")
        )
    )

    val obdCodeDatabase: List<ObdCodeInfo> = listOf(
        ObdCodeInfo(
            code = "P0300",
            title = "Random / Multiple Cylinder Misfire Detected",
            system = "Ignition & Powertrain",
            severity = Severity.HIGH,
            description = "ECU detected rotational deceleration variations indicating one or more cylinders are failing to ignite properly.",
            matchedIssueId = "obd_p0300_misfire"
        ),
        ObdCodeInfo(
            code = "P0562",
            title = "System Voltage Low (< 11.5 Volts)",
            system = "Electrical & Battery",
            severity = Severity.HIGH,
            description = "Battery or alternator voltage has dropped below threshold necessary for reliable electronic module operation.",
            matchedIssueId = "car_dead_battery"
        ),
        ObdCodeInfo(
            code = "P0217",
            title = "Engine Coolant Overtemperature Condition",
            system = "Cooling System",
            severity = Severity.CRITICAL_STRANDED,
            description = "Coolant temperature exceeded safe operational limits (>115°C). Critical danger of cylinder head warp.",
            matchedIssueId = "car_engine_overheating"
        ),
        ObdCodeInfo(
            code = "P0171",
            title = "System Too Lean (Bank 1)",
            system = "Fuel & Air Metering",
            severity = Severity.MODERATE,
            description = "Too much air or too little fuel detected by oxygen sensor. Common with dirty MAF sensor or vacuum leak.",
            matchedIssueId = null
        ),
        ObdCodeInfo(
            code = "P0420",
            title = "Catalyst System Efficiency Below Threshold (Bank 1)",
            system = "Emissions & Exhaust",
            severity = Severity.LOW,
            description = "Catalytic converter is operating below optimum emissions conversion efficiency.",
            matchedIssueId = null
        ),
        ObdCodeInfo(
            code = "P0442",
            title = "Evaporative Emission Control System Leak Detected (Small Leak)",
            system = "EVAP System",
            severity = Severity.LOW,
            description = "Small fuel vapor leak in fuel tank or loose gas cap.",
            matchedIssueId = null
        ),
        ObdCodeInfo(
            code = "P0700",
            title = "Transmission Control System (MIL Request)",
            system = "Automatic Transmission",
            severity = Severity.HIGH,
            description = "Transmission ECU has logged a fault code requesting instrument cluster check engine illumination.",
            matchedIssueId = null
        )
    )

    fun lookupObdCode(code: String): ObdCodeInfo? {
        val clean = code.trim().uppercase()
        return obdCodeDatabase.find { it.code == clean }
    }
}
