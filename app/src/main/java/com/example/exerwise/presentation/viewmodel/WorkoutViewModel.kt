package com.example.exerwise.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exerwise.data.model.Workout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class WorkoutViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val workoutsCollectionRef = db.collection("workouts")

    val workouts: LiveData<List<Workout>> = MutableLiveData()

    init {
        loadWorkouts()
    }

    private fun loadWorkouts() {
        workoutsCollectionRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                // Handle error
                return@addSnapshotListener
            }

            val workoutList = mutableListOf<Workout>()
            for (doc in snapshot!!) {
                val workout = doc.toObject(Workout::class.java)
                workoutList.add(workout)
            }
            (workouts as MutableLiveData).value = workoutList
        }
    }
}