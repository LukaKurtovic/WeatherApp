package hr.dice.luka_kurtovic.weatherapp.ui.settings.model

import org.junit.Assert.assertEquals
import org.junit.Test

class UnitsTest {

    @Test
    fun `test getUnitsOfMeasure for standard returns kmh`() {
        val actual = Units.STANDARD.getUnitsOfMeasure()
        val expected = "km/h"

        assertEquals(expected, actual)
    }

    @Test
    fun `test getUnitsOfMeasure for imperial returns mph`() {
        val actual = Units.IMPERIAL.getUnitsOfMeasure()
        val expected = "mph"

        assertEquals(expected, actual)
    }

    @Test
    fun `test getUnitsOfMeasure for metric returns kmh`() {
        val actual = Units.METRIC.getUnitsOfMeasure()
        val expected = "km/h"

        assertEquals(expected, actual)
    }
}
