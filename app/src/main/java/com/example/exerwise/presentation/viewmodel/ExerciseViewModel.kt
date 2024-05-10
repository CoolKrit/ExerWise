package com.example.exerwise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.exerwise.data.model.Exercise

class ExerciseViewModel : ViewModel() {
    var selectedTitle = ""
    var selectedId = ""
    var selectedExercises = mutableListOf<Exercise>()

    fun addTitle(workoutTitle: String) {
        selectedTitle = workoutTitle
    }

    fun addIdWorkout(workoutId: String) {
        selectedId = workoutId
    }

    fun addExerciseList(exerciseList: List<Exercise>) {
        selectedExercises = exerciseList.toMutableList()
    }

    fun addExercise(exercise: Exercise) {
        selectedExercises.add(exercise)
    }

    fun clear() {
        selectedTitle = ""
        selectedExercises.clear()
    }
}