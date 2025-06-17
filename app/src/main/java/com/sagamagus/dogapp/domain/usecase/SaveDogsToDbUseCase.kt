package com.sagamagus.dogapp.domain.usecase

import com.sagamagus.dogapp.domain.model.DogModel
import com.sagamagus.dogapp.domain.repository.DogRepository

class SaveDogsToDbUseCase(
    private val repository: DogRepository
) : UseCase<List<DogModel>, Unit> {

    override suspend fun invoke(params: List<DogModel>) {
        repository.insertDogs(params)
    }
}