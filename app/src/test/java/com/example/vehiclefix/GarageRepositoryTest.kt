package com.example.vehiclefix

import com.example.vehiclefix.data.models.GarageSpecialty
import com.example.vehiclefix.data.models.VehicleType
import com.example.vehiclefix.data.repository.GarageRepository
import org.junit.Assert.*
import org.junit.Test

class GarageRepositoryTest {

    @Test
    fun testAllGaragesSortedByDistance() {
        val garages = GarageRepository.getAllGarages()
        assertTrue("Garages list should not be empty", garages.isNotEmpty())

        for (i in 0 until garages.size - 1) {
            assertTrue(
                "Garages should be sorted in ascending distance order",
                garages[i].distanceKm <= garages[i + 1].distanceKm
            )
        }
    }

    @Test
    fun testThreeWheelerGarageFilter() {
        val threeWheelerGarages = GarageRepository.getGaragesForVehicleType(VehicleType.THREE_WHEELER)
        assertTrue("Should find garages specializing in 3-Wheelers", threeWheelerGarages.isNotEmpty())

        val hasAutoSpecialist = threeWheelerGarages.any {
            it.specialties.contains(GarageSpecialty.THREE_WHEELER)
        }
        assertTrue("At least one garage should explicitly specialize in 3-Wheelers / Autos", hasAutoSpecialist)
    }

    @Test
    fun testEmergency24x7RescueLookup() {
        val rescue = GarageRepository.getNearestEmergencyRescue()
        assertNotNull("Should have at least one 24/7 emergency tow rescue garage", rescue)
        assertTrue("Rescue garage must be 24x7", rescue?.is24x7 == true)
        assertTrue("Rescue garage must have tow trucks available", rescue?.towTruckAvailable == true)
    }

    @Test
    fun testWhatsAppAndDialUriFormatting() {
        val garages = GarageRepository.getAllGarages()
        val first = garages.first()

        val dialUri = first.getDialUri()
        assertTrue("Dial URI must start with tel:", dialUri.startsWith("tel:"))

        val whatsappUri = first.getWhatsAppUri("Test SOS Message")
        assertTrue("WhatsApp URI must start with https://api.whatsapp.com/send", whatsappUri.startsWith("https://api.whatsapp.com/send"))
        assertTrue("WhatsApp URI must contain URL encoded message", whatsappUri.contains("Test+SOS+Message") || whatsappUri.contains("Test%20SOS%20Message"))
    }
}
