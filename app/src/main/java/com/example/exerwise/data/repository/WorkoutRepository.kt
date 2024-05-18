package com.example.exerwise.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.exerwise.data.model.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class WorkoutRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseStore = FirebaseFirestore.getInstance()

    private val createdWorkoutsCollection =
        firebaseStore.collection("users").document(firebaseAuth.currentUser!!.uid)
            .collection("createdWorkouts")

    private val finishedWorkoutsCollection =
        firebaseStore.collection("users").document(firebaseAuth.currentUser!!.uid)
            .collection("finishedWorkouts")


    fun getCreatedWorkoutList(): LiveData<List<Workout>> {
        val workoutsList = MutableLiveData<List<Workout>>()

        createdWorkoutsCollection.addSnapshotListener { snapshot, error ->
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

    fun getFinishedWorkoutList(): LiveData<List<Workout>> {
        val workoutsList = MutableLiveData<List<Workout>>()

        finishedWorkoutsCollection.addSnapshotListener { snapshot, error ->
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

    fun getLastSevenWorkouts(callback: (List<Workout>) -> Unit) {
        finishedWorkoutsCollection
            .orderBy("id", Query.Direction.ASCENDING)
            .limit(7)
            .get()
            .addOnSuccessListener { result ->
                val workouts = result.map { document ->
                    document.toObject(Workout::class.java).apply { id = document.id }
                }
                callback(workouts)
            }
            .addOnFailureListener { exception ->
                Log.e("WorkoutRepository", "Error getting documents: ", exception)
            }
    }
}