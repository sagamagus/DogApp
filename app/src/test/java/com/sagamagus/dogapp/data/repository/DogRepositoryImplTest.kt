package com.sagamagus.dogapp

import com.sagamagus.dogapp.data.local.DogDao
import com.sagamagus.dogapp.data.local.DogEntity
import com.sagamagus.dogapp.data.mapper.toDomain
import com.sagamagus.dogapp.data.remote.DogApiService
import com.sagamagus.dogapp.data.remote.DogDto
import com.sagamagus.dogapp.data.repository.DogRepositoryImpl
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DogRepositoryImplTest {

    private lateinit var repository: DogRepositoryImpl
    private val apiService: DogApiService = mockk()
    private val dao: DogDao = mockk()

    @Before
    fun setup() {
        repository = DogRepositoryImpl(apiService, dao)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getDogsFromApi returns dogs from API`() = runTest {
        val apiDogs = listOf(
            DogDto("Rex", "Desc", 5, "url1"),
            DogDto("Spots", "Desc", 3, "url2")
        )
        coEvery { apiService.getDogs() } returns apiDogs

        val result = repository.getDogsFromApi()

        assertEquals(2, result.size)
        assertEquals("Rex", result[0].dogName)
    }

    @Test
    fun `getDogsFromDb returns dogs from DB`() = runTest {
        val dbDogs = listOf(
            DogEntity("Rex", "Desc", 5, "url1"),
            DogEntity("Chief", "Desc", 8, "url2")
        )
        coEvery { dao.getDogs() } returns dbDogs

        val result = repository.getDogsFromDb()

        assertEquals(2, result.size)
        assertEquals("Chief", result[1].dogName)
    }

    @Test
    fun `insertDogs inserts dogs into DB`() = runTest {
        val dogs = listOf(
            DogEntity("Rex", "Desc", 5, "url1")
        )
        coEvery { dao.insertDogs(dogs) } just Runs

        repository.insertDogs(dogs.map { it.toDomain() })

        coVerify { dao.insertDogs(dogs) }
    }
}