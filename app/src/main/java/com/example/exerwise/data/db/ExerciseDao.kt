package com.example.exerwise.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.exerwise.data.model.ExerciseEntity

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises")
    suspend fun getAllExercises(): List<ExerciseEntity>

    @Query("SELECT * FROM exercises WHERE name LIKE :name")
    suspend fun getExercisesByName(name: String): List<ExerciseEntity>

    @Query("SELECT * FROM exercises WHERE name LIKE :name AND bodyPart LIKE :bodyPart AND equipment LIKE :equipment")
    suspend fun getExercisesFiltered(name: String, bodyPart: String, equipment: String): List<ExerciseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exercises: List<ExerciseEntity>)

    @Query("DELETE FROM exercises")
    suspend fun deleteAll()
}