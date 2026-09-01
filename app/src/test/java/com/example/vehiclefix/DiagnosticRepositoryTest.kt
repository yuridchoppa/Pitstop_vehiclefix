package com.example.vehiclefix

import com.example.vehiclefix.data.repository.DiagnosticRepository
import com.example.vehiclefix.data.repository.ScanCategory
import org.junit.Assert.*
import org.junit.Test

class DiagnosticRepositoryTest {

    @Test
    fun testVisualPresetsValid() {
        val presets = DiagnosticRepository.visualPresets
        assertTrue("Should have visual scan presets", presets.isNotEmpty())

        for (p in presets) {
            assertEquals(ScanCategory.VISUAL, p.category)
            assertTrue("Confidence should be realistic between 70-100", p.confidence in 70..100)
            assertTrue("Should match at least 1 signal", p.matchedSignals.isNotEmpty())
        }
    }

    @Test
    fun testAudioPresetsValid() {
        val audioPresets = DiagnosticRepository.audioPresets
        assertTrue("Should have audio scan presets", audioPresets.isNotEmpty())

        for (p in audioPresets) {
            assertEquals(ScanCategory.AUDIO, p.category)
            assertTrue("Audio preset should have valid title", p.title.isNotEmpty())
        }
    }

    @Test
    fun testObdLookup() {
        val p0300 = DiagnosticRepository.lookupObdCode("P0300")
        assertNotNull("P0300 should be present in OBD database", p0300)
        assertEquals("P0300", p0300?.code)

        val lowercaseCode = DiagnosticRepository.lookupObdCode("p0217")
        assertNotNull("Case insensitive OBD lookup should work", lowercaseCode)
        assertEquals("P0217", lowercaseCode?.code)

        val nonExistent = DiagnosticRepository.lookupObdCode("P9999")
        assertNull("Non-existent code should return null", nonExistent)
    }
}
