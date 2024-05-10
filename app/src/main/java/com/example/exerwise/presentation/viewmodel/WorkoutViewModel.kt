package com.example.exerwise.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.exerwise.data.model.Workout
import com.example.exerwise.data.repository.WorkoutRepository

class WorkoutViewModel : ViewModel() {
    private val repository = WorkoutRepository()

    val workoutList: LiveData<List<Workout>> = repository.getWorkoutList()
}