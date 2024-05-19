package com.example.exerwise.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exerwise.data.model.Workout
import com.example.exerwise.data.repository.WorkoutRepository
import kotlinx.coroutines.launch

class WorkoutViewModel : ViewModel() {
    private val repository = WorkoutRepository()

    val createdWorkoutList: LiveData<List<Workout>> = repository.getCreatedWorkoutList()
    val finishedWorkoutList: LiveData<List<Workout>> = repository.getFinishedWorkoutList()

    private val _workouts = MutableLiveData<List<Workout>>()
    val workouts: LiveData<List<Workout>> get() = _workouts

    fun deleteWorkout(workoutId: String) {
        viewModelScope.launch {
            repository.deleteWorkout(workoutId)
        }
    }

    fun fetchLastSevenWorkouts() {
        repository.getLastSevenWorkouts { workouts ->
            _workouts.postValue(workouts)
        }
    }
}