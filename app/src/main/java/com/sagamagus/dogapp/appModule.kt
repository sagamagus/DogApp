package com.sagamagus.dogapp

import androidx.room.Room
import com.sagamagus.dogapp.data.local.DogDatabase
import com.sagamagus.dogapp.data.remote.DogApiService
import com.sagamagus.dogapp.data.repository.DogRepositoryImpl
import com.sagamagus.dogapp.domain.repository.DogRepository
import com.sagamagus.dogapp.domain.usecase.GetDogsFromApiUseCase
import com.sagamagus.dogapp.domain.usecase.GetDogsFromDbUseCase
import com.sagamagus.dogapp.domain.usecase.SaveDogsToDbUseCase
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://jsonblob.com/api/")
            .addConverterFactory(MoshiConverterFactory.create())
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


}