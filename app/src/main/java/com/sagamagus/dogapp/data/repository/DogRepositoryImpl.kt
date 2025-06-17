package com.sagamagus.dogapp.data.repository

import com.sagamagus.dogapp.data.local.DogDao
import com.sagamagus.dogapp.data.mapper.toDomain
import com.sagamagus.dogapp.data.mapper.toEntity
import com.sagamagus.dogapp.data.remote.DogApiService
import com.sagamagus.dogapp.domain.model.DogModel
import com.sagamagus.dogapp.domain.repository.DogRepository

class DogRepositoryImpl(
    private val apiService: DogApiService,
    private val dogDao: DogDao
) : DogRepository {

    override suspend fun getDogsFromApi(): List<DogModel> {
        return apiService.getDogs().map { it.toDomain() }
    }

    override suspend fun insertDogs(dogs: List<DogModel>) {
        val entities = dogs.map { it.toEntity() }
        dogDao.insertDogs(entities)
    }

    override suspend fun getDogsFromDb(): List<DogModel> {
        return dogDao.getDogs().map { it.toDomain() }
    }
}