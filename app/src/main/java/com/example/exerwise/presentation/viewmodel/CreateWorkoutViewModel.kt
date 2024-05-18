package com.example.exerwise.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exerwise.data.model.Exercise
import com.example.exerwise.data.model.Set
import com.example.exerwise.data.model.Workout
import com.example.exerwise.utils.FirebaseUtils

class CreateWorkoutViewModel : ViewModel() {

    private val _workout = MutableLiveData<Workout>()
    val workout: LiveData<Workout> get() = _workout

    private val workoutId: String get() = _workout.value?.id ?: ""
    var workoutName: String = ""

    private val _exercises = MutableLiveData<MutableList<Exercise>?>()
    val exercises: LiveData<MutableList<Exercise>?> get() = _exercises

    fun setWorkout(workout: Workout) {
        _workout.value = workout
        _exercises.value = workout.exercises.toMutableList()
    }

    fun addExercise(exercise: Exercise) {
        val currentExercises = _exercises.value?.toMutableList() ?: mutableListOf()
        currentExercises.add(exercise)
        _exercises.value = currentExercises
    }

    fun addSet(exerciseId: Int, set: Set) {
        val updatedExercises = _exercises.value?.map { exercise ->
            if (exercise.id == exerciseId) {
                exercise.exerciseSetsList.add(set)
                exercise.copy(exerciseSetsList = exercise.exerciseSetsList)
            } else {
                exercise
            }
        }
        _exercises.value = updatedExercises as MutableList<Exercise>?
    }

    fun removeSet(exerciseId: Int, setNumber: Int) {
        val updatedExercises = _exercises.value?.map { exercise ->
            if (exercise.id == exerciseId) {
                val updatedSets = exercise.exerciseSetsList.toMutableList()
                if (setNumber >= 0 && setNumber < updatedSets.size) {
                    updatedSets.removeAt(setNumber)
                    updatedSets.forEachIndexed { index, updatedSet ->
                        updatedSet.setNumber = index + 1
                    }
                }
                exercise.copy(exerciseSetsList = updatedSets)
            } else {
                exercise
            }
        }
        _exercises.value = updatedExercises as MutableList<Exercise>?
    }

    fun saveWorkout() {
        val updatedWorkout = _workout.value?.copy(
            id = workoutId,
            name = workoutName,
            exercises = _exercises.value ?: mutableListOf()
        )
        if (updatedWorkout != null) {
            try {
                FirebaseUtils.createWorkout(updatedWorkout)
            } catch (_: Exception) {
            }
        }
    }
}
