package com.example.exerwise.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exerwise.data.model.ExerciseResponse

class SharedViewModel : ViewModel() {

    private val _selectedExercise = MutableLiveData<ExerciseResponse?>()
    val selectedExercise: LiveData<ExerciseResponse?> get() = _selectedExercise

    fun selectExercise(exercise: ExerciseResponse) {
        _selectedExercise.value = exercise
    }

    fun clearData() {
        _selectedExercise.value = null
    }
}