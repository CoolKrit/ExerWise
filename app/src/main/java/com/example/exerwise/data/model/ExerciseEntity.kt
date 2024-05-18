package com.example.exerwise.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val bodyPart: String,
    val equipment: String,
    val target: String,
    val gifUrl: String,
    val instructions: List<String>
) {
    fun toDomainModel(): ExerciseResponse {
        return ExerciseResponse(
            id = id,
            name = name,
            bodyPart = bodyPart,
            equipment = equipment,
            target = target,
            gifUrl = gifUrl,
            instructions = instructions,
        )
    }
}