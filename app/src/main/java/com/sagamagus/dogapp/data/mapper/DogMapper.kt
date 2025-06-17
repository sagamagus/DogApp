package com.sagamagus.dogapp.data.mapper

import com.sagamagus.dogapp.data.local.DogEntity
import com.sagamagus.dogapp.data.remote.DogDto
import com.sagamagus.dogapp.domain.model.DogModel

// API -> Dominio
fun DogDto.toDomain(): DogModel {
    return DogModel(
        dogName = dogName,
        description = description,
        age = age,
        image = image
    )
}

// DB -> Dominio
fun DogEntity.toDomain(): DogModel {
    return DogModel(
        dogName = dogName,
        description = description,
        age = age,
        image = image
    )
}

// Dominio -> DB
fun DogModel.toEntity(): DogEntity {
    return DogEntity(
        dogName = dogName,
        description = description,
        age = age,
        image = image
    )
}