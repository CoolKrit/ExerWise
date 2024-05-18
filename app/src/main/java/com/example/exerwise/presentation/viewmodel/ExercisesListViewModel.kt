package com.example.exerwise.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.exerwise.data.model.ExerciseEntity
import com.example.exerwise.data.model.ExerciseResponse
import com.example.exerwise.data.repository.ExercisesListRepository
import kotlinx.coroutines.launch

class ExercisesListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ExercisesListRepository(application)

    private val _exercises = MutableLiveData<List<ExerciseResponse>>()
    val exercises: LiveData<List<ExerciseResponse>> get() = _exercises

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchExercises() {
        viewModelScope.launch {
            try {
                val response = repository.getExercises()
                if (response.isSuccessful && response.body() != null) {
                    _exercises.postValue(response.body())
                    repository.deleteAllExercises()
                    val exercisesToSave = response.body()!!.map {
                        ExerciseEntity(it.id, it.name, it.bodyPart, it.equipment, it.target, it.gifUrl, it.instructions)
                    }
                    repository.insertAllExercises(exercisesToSave)
                } else {
                    val errorMessage = "Error fetching data: ${response.errorBody()?.string()}"
                    _error.postValue(errorMessage)
                }
            } catch (e: Exception) {
                loadLocalExercises()
            }
        }
    }

    fun fetchExercisesFiltered(name: String, bodyPart: String, equipment: String) {
        viewModelScope.launch {
            loadLocalExercisesFiltered(name, bodyPart, equipment)
        }
    }
    private suspend fun loadLocalExercises() {
        val localExercises = repository.getAllLocalExercises()
        _exercises.postValue(localExercises.map {
            ExerciseResponse(it.id, it.name, it.bodyPart, it.equipment, it.target, it.gifUrl, it.instructions)
        })
    }
    private suspend fun loadLocalExercisesFiltered(name: String, bodyPart: String, equipment: String) {
        val localExercises = repository.getLocalExercisesFiltered(name, bodyPart, equipment)
        _exercises.postValue(localExercises.map {
            ExerciseResponse(it.id, it.name, it.bodyPart, it.equipment, it.target, it.gifUrl, it.instructions)
        })
    }
}