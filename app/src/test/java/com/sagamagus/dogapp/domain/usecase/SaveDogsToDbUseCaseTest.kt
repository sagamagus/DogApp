package com.sagamagus.dogapp

import com.sagamagus.dogapp.domain.model.DogModel
import com.sagamagus.dogapp.domain.repository.DogRepository
import com.sagamagus.dogapp.domain.usecase.SaveDogsToDbUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SaveDogsToDbUseCaseTest {

    private val repository: DogRepository = mockk(relaxed = true)
    private lateinit var useCase: SaveDogsToDbUseCase

    @Before
    fun setUp() {
        useCase = SaveDogsToDbUseCase(repository)
    }

    @Test
    fun `execute saves dogs to db`() = runTest {
        val dogs = listOf(
            DogModel("Chief", "desc", 8, "url")
        )

        useCase.invoke(dogs)

        coVerify { repository.insertDogs(dogs) }
    }
}