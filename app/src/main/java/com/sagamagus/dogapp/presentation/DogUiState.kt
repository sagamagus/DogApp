package com.sagamagus.dogapp.presentation

import com.sagamagus.dogapp.domain.model.DogModel


data class DogUiState(
    val isLoading: Boolean = false,
    val dogs: List<DogModel> = emptyList(),
    val error: String? = null
)