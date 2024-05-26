package com.example.exerwise.data.api

import com.example.exerwise.data.model.ExerciseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * @file ApiService.kt
 * @brief Определение интерфейса для API сервиса.
 *
 * Этот интерфейс определяет методы для взаимодействия с API упражнений, включая
 * получение списка упражнений и поиск упражнений по имени. Используется Retrofit
 * для HTTP запросов и заголовки для аутентификации API.
 *
 * @autor
 */
interface ApiService {
    /**
     * @brief Получает список упражнений.
     *
     * Этот метод отправляет GET запрос для получения списка упражнений от API.
     * Ожидается, что ответ будет содержать до 1300 упражнений.
     *
     * @return Ответ, содержащий список объектов ExerciseResponse.
     */
    @Headers("X-RapidAPI-Key: 5d5b39dce6mshe7c6d2c23a4b929p11f46ejsnc849c49cfd48")
    @GET("exercises?limit=1300")
    suspend fun getExercises(): Response<List<ExerciseResponse>>

    /**
     * @brief Поиск упражнений по имени.
     *
     * Этот метод отправляет GET запрос для поиска упражнений на основе предоставленного
     * имени упражнения. Ожидается, что ответ будет содержать список соответствующих объектов ExerciseResponse.
     *
     * @param name Имя упражнения для поиска.
     * @return Ответ, содержащий список объектов ExerciseResponse, соответствующих критериям поиска.
     */
    @Headers("X-RapidAPI-Key: 5d5b39dce6mshe7c6d2c23a4b929p11f46ejsnc849c49cfd48")
    @GET("exercises/name/{name}")
    suspend fun getExercisesByName(@Path("name") name: String): Response<List<ExerciseResponse>>
}