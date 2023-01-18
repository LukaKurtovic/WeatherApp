package hr.dice.luka_kurtovic.weatherapp.ui.bottom_navigation_container_screen

import app.cash.turbine.test
import hr.dice.luka_kurtovic.weatherapp.MainDispatcherRule
import hr.dice.luka_kurtovic.weatherapp.data.LocationRepository
import hr.dice.luka_kurtovic.weatherapp.data.model.Location
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class CustomTopAppBarViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var locationRepository: LocationRepository

    private lateinit var viewModel: CustomTopAppBarViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = CustomTopAppBarViewModel(locationRepository)

        every { locationRepository.getActiveLocation() } returns flowOf(
            Location(
                "Osijek",
                10f,
                10f,
                true
            )
        )
    }

    @Test
    fun `test getActiveLocation`() = runTest {
        val job = launch {
            viewModel.uiState.test {
                val actual = awaitItem().city
                val expected = "Osijek"
                assertEquals(expected, actual)
                cancelAndIgnoreRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun `test updateDate`() = runTest {
        val newDate = LocalDate.of(2022, 10, 10)

        val job = launch {
            viewModel.uiState.test {
                val actual = awaitItem().date
                assertEquals(newDate, actual)
                cancelAndIgnoreRemainingEvents()
            }
        }
        viewModel.updateDate(newDate)
        job.join()
        job.cancel()
    }
}
