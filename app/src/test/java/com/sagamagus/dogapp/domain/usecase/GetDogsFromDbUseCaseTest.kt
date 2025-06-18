package com.sagamagus.dogapp.domain.usecase

import com.sagamagus.dogapp.domain.model.DogModel
import com.sagamagus.dogapp.domain.repository.DogRepository
import io.mockk.coVerify
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetDogsFromDbUseCaseTest {

    private val repository: DogRepository = mockk()
    private lateinit var useCase: GetDogsFromDbUseCase

    @Before
    fun setUp() {
        useCase = GetDogsFromDbUseCase(repository)
    }

    @Test
    fun `execute returns dogs from db`() = runTest {
        val dogs = listOf(
            DogModel("Boss", "desc", 4, "url")
        )
        coEvery { repository.getDogsFromDb() } returns dogs

        val result = useCase.invoke(Unit)

        assertEquals(dogs, result)
        coVerify { repository.getDogsFromDb() }
    }
}