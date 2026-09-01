package com.example.vehiclefix.data.models

enum class VehicleType(val displayName: String, val emoji: String) {
    CAR("Car / SUV", "🚗"),
    THREE_WHEELER("3-Wheeler / Auto", "🛺"),
    TWO_WHEELER("Motorbike / Scooter", "🏍️"),
    COMMERCIAL("Commercial / Truck", "🚛"),
    ALL("All Vehicles", "🚘")
}

enum class Severity(val label: String, val colorHex: String) {
    LOW("Low - Minor Issue", "#2ECC71"),
    MODERATE("Moderate - Attention Needed", "#F1C40F"),
    HIGH("High - Needs Immediate Fix", "#E67E22"),
    CRITICAL_STRANDED("Critical - Do NOT Drive / Stranded", "#E74C3C")
}

enum class Difficulty(val label: String, val isProRecommended: Boolean, val badgeColorHex: String) {
    EASY_DIY("Easy DIY (10-20 min)", false, "#2ECC71"),
    MODERATE_DIY("Moderate DIY (30-45 min)", false, "#3498DB"),
    DIFFICULT_PRO_RECOMMENDED("Difficult (Pro Recommended)", true, "#E67E22"),
    EXPERT_GARAGE_ONLY("Expert (Garage Only)", true, "#E74C3C")
}

data class ToolItem(
    val id: String,
    val name: String,
    val isMandatory: Boolean = true,
    val alternative: String? = null
)

data class PartItem(
    val id: String,
    val name: String,
    val approxPrice: String,
    val isMandatory: Boolean = true
)

data class FixStep(
    val stepNumber: Int,
    val title: String,
    val description: String,
    val proTip: String? = null,
    val warning: String? = null,
    val timerSeconds: Int = 0
)

data class VehicleIssue(
    val id: String,
    val title: String,
    val vehicleType: VehicleType,
    val category: String,
    val severity: Severity,
    val difficulty: Difficulty,
    val estimatedTime: String,
    val summary: String,
    val symptoms: List<String>,
    val commonCauses: List<String>,
    val safetyPrecautions: List<String>,
    val toolsNeeded: List<ToolItem>,
    val partsNeeded: List<PartItem>,
    val steps: List<FixStep>,
    val obdCode: String? = null,
    val audioSymptom: String? = null,
    val visualSymptom: String? = null,
    val isStrandedHazard: Boolean = false
)
