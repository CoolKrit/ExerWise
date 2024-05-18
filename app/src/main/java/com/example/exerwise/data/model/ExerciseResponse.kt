package com.example.exerwise.data.model

data class ExerciseResponse(
    val id: Int,
    val name: String,
    val bodyPart: String,
    val equipment: String,
    val target: String,
    val gifUrl: String,
    val instructions: List<String>
) {
    fun toDomainModel(): Exercise {
        return Exercise(
            id = id,
            name = name,
            bodyPart = bodyPart,
            equipment = equipment,
            target = target,
            gifUrl = gifUrl,
            instructions = instructions,
            exerciseSetsList = mutableListOf()
        )
    }
}