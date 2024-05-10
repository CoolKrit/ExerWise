package com.example.exerwise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.exerwise.data.model.Exercise

class ExerciseViewModel : ViewModel() {
    var selectedTitle = ""
    val selectedExercises = mutableListOf<Exercise>()

    fun addTitle(workoutTitle: String) {
        selectedTitle = workoutTitle
    }

    fun addExercise(exercise: Exercise) {
        selectedExercises.add(exercise)
    }

    fun clear() {
        selectedTitle = ""
        selectedExercises.clear()
    }
}