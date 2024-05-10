package com.example.exerwise.presentation.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ExerciseApiClient {

    private const val BASE_URL = "https://api.api-ninjas.com/v1/"

    fun create(): ExerciseApiService {
        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("X-Api-Key", "R2eTebiHJNNCB6YqZt1u9w==NoVVNSjUnP3lTNys")
                    .build()
                chain.proceed(request)
            }
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ExerciseApiService::class.java)
    }
}