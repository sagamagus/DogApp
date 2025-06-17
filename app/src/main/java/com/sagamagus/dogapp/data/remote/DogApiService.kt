package com.sagamagus.dogapp.data.remote

import retrofit2.http.GET

interface DogApiService {
    @GET("1151549092634943488")
    suspend fun getDogs(): List<DogDto>
}
