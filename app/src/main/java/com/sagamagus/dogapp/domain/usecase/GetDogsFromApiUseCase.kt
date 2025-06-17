package com.sagamagus.dogapp.domain.usecase

import com.sagamagus.dogapp.domain.model.DogModel
import com.sagamagus.dogapp.domain.repository.DogRepository

class GetDogsFromApiUseCase(
    private val repository: DogRepository
) : UseCase<Unit, List<DogModel>> {

    override suspend fun invoke(params: Unit): List<DogModel> {
        return repository.getDogsFromApi()
    }
}