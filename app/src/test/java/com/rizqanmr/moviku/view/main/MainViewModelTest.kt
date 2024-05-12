package com.rizqanmr.moviku.view.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rizqanmr.moviku.model.GenresItem
import com.rizqanmr.moviku.model.GenresModel
import com.rizqanmr.moviku.repository.AppRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val testDispatcher = MainDispatcherRule()

    // Mock dependencies
    private lateinit var appRepository: AppRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        appRepository = mockk()
        viewModel = MainViewModel(appRepository)
    }

    @Test
    fun `getGenres should update live data`() = runBlocking {
        // Given
        val fakeGenresModel = fakeGenresModel()
        coEvery { appRepository.getGenres() } returns fakeGenresModel

        // When
        viewModel.getGenres()

        // Then
        assert(viewModel.genres.value == fakeGenresModel)
    }

    companion object {
        fun fakeGenresModel() = GenresModel(
            genres = listOf(
                GenresItem(name = "action", id = 1),
                GenresItem(name = "drama", id = 2)
            )
        )
    }
}