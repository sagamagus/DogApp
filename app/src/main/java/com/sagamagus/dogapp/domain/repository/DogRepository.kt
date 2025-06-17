package com.sagamagus.dogapp.domain.repository

import com.sagamagus.dogapp.domain.model.DogModel

interface DogRepository {
    suspend fun getDogsFromApi(): List<DogModel>
    suspend fun insertDogs(dogs: List<DogModel>)
    suspend fun getDogsFromDb(): List<DogModel>
}