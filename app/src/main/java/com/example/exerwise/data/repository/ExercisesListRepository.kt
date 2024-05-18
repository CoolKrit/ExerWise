package com.example.exerwise.data.repository

import android.content.Context
import com.example.exerwise.data.api.ApiService
import com.example.exerwise.data.api.RetrofitInstance
import com.example.exerwise.data.db.AppDatabase
import com.example.exerwise.data.db.ExerciseDao
import com.example.exerwise.data.model.ExerciseEntity
import com.example.exerwise.data.model.ExerciseResponse
import retrofit2.Response

class ExercisesListRepository(context: Context) {
    private val api: ApiService = RetrofitInstance.api
    private val exerciseDao: ExerciseDao = AppDatabase.getDatabase(context).exerciseDao()

    suspend fun getExercises(): Response<List<ExerciseResponse>> {
        return api.getExercises()
    }

    suspend fun getExercisesByName(name: String): Response<List<ExerciseResponse>> {
        return api.getExercisesByName(name)
    }

    suspend fun getAllLocalExercises(): List<ExerciseEntity> {
        return exerciseDao.getAllExercises()
    }

    suspend fun getLocalExercisesByName(name: String): List<ExerciseEntity> {
        return exerciseDao.getExercisesByName(name)
    }

    suspend fun getLocalExercisesFiltered(name: String, bodyPart: String, equipment: String): List<ExerciseEntity> {
        return exerciseDao.getExercisesFiltered("%$name%", "%$bodyPart%", "%$equipment%")
    }

    suspend fun insertAllExercises(exercises: List<ExerciseEntity>) {
        exerciseDao.insertAll(exercises)
    }

    suspend fun deleteAllExercises() {
        exerciseDao.deleteAll()
    }
}