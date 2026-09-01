package com.example.vehiclefix

import com.example.vehiclefix.data.models.Difficulty
import com.example.vehiclefix.data.models.Severity
import com.example.vehiclefix.data.models.VehicleType
import com.example.vehiclefix.data.repository.VehicleIssueRepository
import org.junit.Assert.*
import org.junit.Test

class VehicleIssueRepositoryTest {

    @Test
    fun testAllIssuesLoaded() {
        val issues = VehicleIssueRepository.getAllIssues()
        assertTrue("Issue database should contain multiple diagnostics", issues.isNotEmpty())
        assertTrue("Should have at least 6 detailed vehicle repair cases", issues.size >= 6)
    }

    @Test
    fun testThreeWheelerIssuesPresent() {
        val threeWheelerIssues = VehicleIssueRepository.getIssuesByVehicleType(VehicleType.THREE_WHEELER)
        assertTrue("Should have dedicated 3-wheeler/auto issues", threeWheelerIssues.isNotEmpty())

        val clutchIssue = threeWheelerIssues.find { it.id == "auto_clutch_cable" }
        assertNotNull("Should contain broken clutch cable issue for 3-wheelers", clutchIssue)
        assertEquals(VehicleType.THREE_WHEELER, clutchIssue?.vehicleType)
        assertTrue("Clutch cable fix should have at least 3 tools", (clutchIssue?.toolsNeeded?.size ?: 0) >= 3)
        assertTrue("Clutch cable fix should have replacement parts list", (clutchIssue?.partsNeeded?.size ?: 0) >= 1)

        val brakeShoeIssue = threeWheelerIssues.find { it.id == "auto_brake_shoe_pulling" }
        assertNotNull("Should contain 3-wheeler brake shoe pulling issue", brakeShoeIssue)
        assertEquals(VehicleType.THREE_WHEELER, brakeShoeIssue?.vehicleType)

        val eRickshawIssue = threeWheelerIssues.find { it.id == "e_rickshaw_battery_imbalance" }
        assertNotNull("Should contain e-rickshaw battery imbalance issue", eRickshawIssue)
        assertEquals(VehicleType.THREE_WHEELER, eRickshawIssue?.vehicleType)
    }

    @Test
    fun testCarIssuesPresent() {
        val carIssues = VehicleIssueRepository.getIssuesByVehicleType(VehicleType.CAR)
        assertTrue("Should have car issues", carIssues.isNotEmpty())

        val flatTire = carIssues.find { it.id == "car_flat_tire" }
        assertNotNull("Should contain flat tire issue", flatTire)
        assertEquals(Difficulty.EASY_DIY, flatTire?.difficulty)

        val overheat = carIssues.find { it.id == "car_engine_overheating" }
        assertNotNull("Should contain engine overheating issue", overheat)
        assertEquals(Severity.CRITICAL_STRANDED, overheat?.severity)
    }

    @Test
    fun testSearchFunctionality() {
        val resultsClutch = VehicleIssueRepository.searchIssues("clutch")
        assertTrue("Searching 'clutch' should find relevant issues", resultsClutch.any { it.title.contains("Clutch", ignoreCase = true) })

        val resultsBattery = VehicleIssueRepository.searchIssues("battery")
        assertTrue("Searching 'battery' should find battery issues", resultsBattery.any { it.id == "car_dead_battery" })

        val resultsOverheat = VehicleIssueRepository.searchIssues("coolant")
        assertTrue("Searching 'coolant' should find cooling issues", resultsOverheat.any { it.id == "car_engine_overheating" })
    }

    @Test
    fun testObdCodeLookup() {
        val p0300Issue = VehicleIssueRepository.findByObdCode("P0300")
        assertNotNull("P0300 code should map to misfire issue", p0300Issue)
        assertEquals("obd_p0300_misfire", p0300Issue?.id)

        val p0562Issue = VehicleIssueRepository.findByObdCode("p0562")
        assertNotNull("p0562 lowercase should map to battery voltage issue", p0562Issue)
        assertEquals("car_dead_battery", p0562Issue?.id)
    }

    @Test
    fun testEveryIssueHasToolsAndSteps() {
        val issues = VehicleIssueRepository.getAllIssues()
        for (issue in issues) {
            assertTrue("Issue '${issue.title}' must have tools needed", issue.toolsNeeded.isNotEmpty())
            assertTrue("Issue '${issue.title}' must have step-by-step fix guide", issue.steps.isNotEmpty())
            assertTrue("Issue '${issue.title}' must have safety precautions", issue.safetyPrecautions.isNotEmpty())
        }
    }
}
