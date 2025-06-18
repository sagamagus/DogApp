package com.sagamagus.dogapp

import androidx.room.Room
import com.sagamagus.dogapp.data.local.DogDatabase
import com.sagamagus.dogapp.data.remote.DogApiService
import com.sagamagus.dogapp.data.repository.DogRepositoryImpl
import com.sagamagus.dogapp.domain.repository.DogRepository
import com.sagamagus.dogapp.domain.usecase.GetDogsFromApiUseCase
import com.sagamagus.dogapp.domain.usecase.GetDogsFromDbUseCase
import com.sagamagus.dogapp.domain.usecase.SaveDogsToDbUseCase
import com.sagamagus.dogapp.presentation.DogViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    // Retrofit
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://jsonblob.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
            .create(DogApiService::class.java)
    }

    // Room Database
    single {
        Room.databaseBuilder(
            get(),
            DogDatabase::class.java,
            "dog_database"
        ).build()
    }

    // DogDao
    single { get<DogDatabase>().dogDao() }

    // Repository
    single<DogRepository> {
        DogRepositoryImpl(
            apiService = get(),
            dogDao = get()
        )
    }

    // UseCases
    factory { GetDogsFromApiUseCase(repository = get()) }
    factory { GetDogsFromDbUseCase(repository = get()) }
    factory { SaveDogsToDbUseCase(repository = get()) }

    // ViewModel
    viewModel {
        DogViewModel(
            getDogsFromApiUseCase = get(),
            getDogsFromDbUseCase = get(),
            saveDogsToDbUseCase = get()
        )
    }
}