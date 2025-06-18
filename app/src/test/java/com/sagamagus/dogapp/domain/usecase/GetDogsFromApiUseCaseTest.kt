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
class GetDogsFromApiUseCaseTest {

    private val repository: DogRepository = mockk()
    private lateinit var useCase: GetDogsFromApiUseCase

    @Before
    fun setUp() {
        useCase = GetDogsFromApiUseCase(repository)
    }

    @Test
    fun `execute returns list of dogs from api`() = runTest {
        val dogs = listOf(
            DogModel("Rex", "desc", 5, "url")
        )
        coEvery { repository.getDogsFromApi() } returns dogs

        val result = useCase.invoke(Unit)

        assertEquals(dogs, result)
        coVerify { repository.getDogsFromApi() }
    }
}