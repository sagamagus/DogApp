package com.sagamagus.dogapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagamagus.dogapp.domain.usecase.GetDogsFromApiUseCase
import com.sagamagus.dogapp.domain.usecase.GetDogsFromDbUseCase
import com.sagamagus.dogapp.domain.usecase.SaveDogsToDbUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DogViewModel(
    private val getDogsFromApiUseCase: GetDogsFromApiUseCase,
    private val saveDogsToDbUseCase: SaveDogsToDbUseCase,
    private val getDogsFromDbUseCase: GetDogsFromDbUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DogUiState())
    val uiState: StateFlow<DogUiState> = _uiState

    init {
        getDogs()
    }

    private fun getDogs() {
        viewModelScope.launch {
            _uiState.value = DogUiState(isLoading = true)

            // Try getting from DB first
            val localDogs = getDogsFromDbUseCase.invoke(Unit)
            if (localDogs.isNotEmpty()) {
                _uiState.value = DogUiState(dogs = localDogs)
            } else {
                try {
                    val remoteDogs = getDogsFromApiUseCase.invoke(Unit)
                    _uiState.value = DogUiState(dogs = remoteDogs)
                    saveDogsToDbUseCase.invoke(remoteDogs)
                } catch (e: Exception) {
                    _uiState.value = DogUiState(error = e.message ?: "Unknown error")
                }
            }
        }
    }
}