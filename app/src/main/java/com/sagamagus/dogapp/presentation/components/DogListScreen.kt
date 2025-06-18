package com.sagamagus.dogapp.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sagamagus.dogapp.R
import com.sagamagus.dogapp.domain.model.DogModel
import com.sagamagus.dogapp.presentation.DogUiState


@Composable
fun DogListScreen(state: DogUiState) {
    Scaffold(
        topBar = {
            NavTopBar(
                title = stringResource(R.string.title_bar),
                canNavigateBack = true
            )
        }
    ) {paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator()
                }
                state.error != null -> {
                    Text(
                        text = "Error: ${state.error}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else -> {
                    DogList(dogs = state.dogs)
                }
            }
        }
    }

}

@Composable
fun DogList(dogs: List<DogModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(10.dp)
    ) {
        items(dogs) { dog ->
            DogItem(dog = dog)
        }
    }
}

