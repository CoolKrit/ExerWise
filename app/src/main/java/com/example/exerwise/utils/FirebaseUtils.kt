package com.example.exerwise.utils

import com.example.exerwise.data.model.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseUtils {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseStore = FirebaseFirestore.getInstance()

    fun createWorkout(workout: Workout) {
        val workoutMap = hashMapOf(
            "id" to workout.id,
            "name" to workout.name,
            "exercises" to workout.exercises
        )
        firebaseStore.collection("users").document(firebaseAuth.currentUser!!.uid).collection("createdWorkouts").document(workout.id).set(workoutMap)
    }

    fun finishWorkout(workout: Workout) {
        val workoutMap = hashMapOf(
            "id" to workout.id,
            "name" to workout.name,
            "duration" to workout.duration,
            "volume" to workout.volume,
            "sets" to  workout.sets,
            "exercises" to workout.exercises
        )
        firebaseStore.collection("users").document(firebaseAuth.currentUser!!.uid).collection("finishedWorkouts").document(workout.id).set(workoutMap)
    }
}