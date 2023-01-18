package hr.dice.luka_kurtovic.weatherapp.data

import hr.dice.luka_kurtovic.weatherapp.data.model.Location
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LocationRepositoryTest {
    private lateinit var locationRepository: LocationRepository

    private var location = Location("Osijek", 10f, 10f, true)

    @MockK
    private lateinit var locationDao: LocationDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        locationRepository = LocationRepository(locationDao)
    }

    @Test
    fun `test insertLocation`() = runTest {
        coEvery { locationDao.insert(any()) } just Runs

        locationRepository.insertLocation(location)

        coVerify {
            locationDao.insert(any())
        }
    }

    @Test
    fun `test updateLocation`() = runTest {
        coEvery { locationDao.update(any()) } just Runs

        locationRepository.updateLocation(location)

        coVerify {
            locationDao.update(any())
        }
    }

    @Test
    fun `test getActiveLocation`() = runTest {
        coEvery { locationDao.getActiveLocation() } returns flowOf(location)

        val actual = locationRepository.getActiveLocation().first()

        assertEquals(location, actual)
    }

    @Test
    fun `test isLocationSaved`() = runTest {
        coEvery { locationDao.isLocationSaved() } returns true

        val isLocationSaved = locationRepository.isLocationSaved()

        assertTrue(isLocationSaved)
    }
}
