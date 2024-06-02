package com.example.exerwise.data.api

import com.example.exerwise.data.model.ExerciseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
    @Headers("X-RapidAPI-Key: 5d5b39dce6mshe7c6d2c23a4b929p11f46ejsnc849c49cfd48")
    @GET("exercises?limit=1300")
    suspend fun getExercises(): Response<List<ExerciseResponse>>

    @Headers("X-RapidAPI-Key: 5d5b39dce6mshe7c6d2c23a4b929p11f46ejsnc849c49cfd48")
    @GET("exercises/name/{name}")
    suspend fun getExercisesByName(@Path("name") name: String): Response<List<ExerciseResponse>>
}