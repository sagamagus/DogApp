package com.sagamagus.dogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sagamagus.dogapp.presentation.DogViewModel
import com.sagamagus.dogapp.presentation.components.DogListScreen
import com.sagamagus.dogapp.ui.theme.DogAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val dogViewModel: DogViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogAppTheme {
                Surface {
                    val state = dogViewModel.uiState.collectAsStateWithLifecycle().value
                    DogListScreen(state = state)
                }
            }
        }
    }
}