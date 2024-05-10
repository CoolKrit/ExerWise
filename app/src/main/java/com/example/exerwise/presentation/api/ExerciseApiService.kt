package com.example.exerwise.presentation.api

import com.example.exerwise.data.model.Exercise
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ExerciseApiService {

    @GET("exercises")
    fun getExercisesByMuscleType(
        @Header("X-Api-Key") apiKey: String,
        @Query("muscle") muscleType: String
    ): Call<List<Exercise>>
}