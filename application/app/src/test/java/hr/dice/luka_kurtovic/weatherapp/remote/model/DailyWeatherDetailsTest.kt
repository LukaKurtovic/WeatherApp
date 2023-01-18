package hr.dice.luka_kurtovic.weatherapp.remote.model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DailyWeatherDetailsTest {
    private lateinit var dailyWeatherDetails: DailyWeatherDetails

    @Before
    fun setUp() {
        dailyWeatherDetails = DailyWeatherDetails(
            "2022-12-29",
            10.0,
            10.0,
            10.0,
            10.0,
            100,
            1000.0,
            90.0,
            10.0,
            10.0,
            10.0,
            10.0,
            "",
            "",
            2.0,
            "",
            "",
            "sunny"
        )
    }

    @Test
    fun `test getIconUrl`() {
        val actual = dailyWeatherDetails.iconUrl
        val expected = "https://raw.githubusercontent.com/visualcrossing/WeatherIcons/main/" +
            "PNG/2nd%20Set%20-%20Color/${dailyWeatherDetails.icon}.png"

        assertEquals(expected, actual)
    }

    @Test
    fun `test toUpperCase for abc returns Abc`() {
        val expected = "Abc"
        val actual = toUpperCase("abc")

        assertEquals(expected, actual)
    }
}
