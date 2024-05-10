package com.example.exerwise.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.exerwise.data.model.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WorkoutRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseStore = FirebaseFirestore.getInstance()

    private val workoutsCollection =
        firebaseStore.collection("users").document(firebaseAuth.currentUser!!.uid)
            .collection("workouts")

    fun getWorkoutList(): LiveData<List<Workout>> {
        val workoutsList = MutableLiveData<List<Workout>>()

        workoutsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            val workouts = mutableListOf<Workout>()
            for (document in snapshot!!.documents) {
                val item = document.toObject(Workout::class.java)
                item?.let {
                    workouts.add(it)
                }
            }

            workoutsList.value = workouts
        }

        return workoutsList
    }
}