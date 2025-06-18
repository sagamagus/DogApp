package com.sagamagus.dogapp.presentation


import com.sagamagus.dogapp.domain.model.DogModel
import com.sagamagus.dogapp.domain.usecase.GetDogsFromApiUseCase
import com.sagamagus.dogapp.domain.usecase.GetDogsFromDbUseCase
import com.sagamagus.dogapp.domain.usecase.SaveDogsToDbUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DogViewModelTest {

    private val getDogsFromApiUseCase: GetDogsFromApiUseCase = mockk()
    private val saveDogsToDbUseCase: SaveDogsToDbUseCase = mockk(relaxed = true)
    private val getDogsFromDbUseCase: GetDogsFromDbUseCase = mockk()

    private lateinit var viewModel: DogViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getDogs loads dogs from DB if available`() = runTest {
        val localDogs = listOf(
            DogModel("Rex", "Desc", 5, "url1")
        )
        coEvery { getDogsFromDbUseCase.invoke(Unit) } returns localDogs
        viewModel = DogViewModel(getDogsFromApiUseCase, saveDogsToDbUseCase, getDogsFromDbUseCase)
        testDispatcher.scheduler.advanceUntilIdle()
        val state = viewModel.uiState.first()
        assertEquals(localDogs, state.dogs)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `getDogs fetches from API and saves to DB if DB is empty`() = runTest {
        val apiDogs = listOf(
            DogModel("Chief", "Desc", 8, "url2")
        )
        coEvery { getDogsFromDbUseCase.invoke(Unit) } returns emptyList()
        coEvery { getDogsFromApiUseCase.invoke(Unit) } returns apiDogs

        viewModel = DogViewModel(getDogsFromApiUseCase, saveDogsToDbUseCase, getDogsFromDbUseCase)
        testDispatcher.scheduler.advanceUntilIdle()
        val state = viewModel.uiState.first()
        assertEquals(apiDogs, state.dogs)
        coVerify { saveDogsToDbUseCase.invoke(apiDogs) }
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `getDogs sets error when API call fails`() = runTest {
        coEvery { getDogsFromDbUseCase.invoke(Unit) } returns emptyList()
        coEvery { getDogsFromApiUseCase.invoke(Unit) } throws Exception("API error")

        viewModel = DogViewModel(getDogsFromApiUseCase, saveDogsToDbUseCase, getDogsFromDbUseCase)
        testDispatcher.scheduler.advanceUntilIdle()
        val state = viewModel.uiState.first()
        assertEquals("API error", state.error)
        assertEquals(false, state.isLoading)
    }
}