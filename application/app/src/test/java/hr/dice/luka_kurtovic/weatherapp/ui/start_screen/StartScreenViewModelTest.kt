package hr.dice.luka_kurtovic.weatherapp.ui.start_screen

import app.cash.turbine.test
import hr.dice.luka_kurtovic.weatherapp.MainDispatcherRule
import hr.dice.luka_kurtovic.weatherapp.data.LocationRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StartScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var locationRepository: LocationRepository

    private lateinit var viewModel: StartScreenViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { locationRepository.isLocationSaved() } returns true
    }

    @Test
    fun `test isLocationSaved`() = runTest {
        viewModel = StartScreenViewModel(locationRepository)
        val job = launch {
            viewModel.isLocationSaved.test {
                val actual = awaitItem()
                val expected = true
                assertEquals(expected, actual)
                coVerify { locationRepository.isLocationSaved() }
                cancelAndIgnoreRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }
}
