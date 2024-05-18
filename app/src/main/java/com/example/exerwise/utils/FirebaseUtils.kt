package com.example.exerwise.utils

import com.example.exerwise.data.model.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseUtils {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun createWorkout(workout: Workout) {
        val workoutMap = hashMapOf(
            "id" to workout.id,
            "name" to workout.name,
            "exercises" to workout.exercises
        )
        db.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid).collection("createdWorkouts").document(workout.id).set(workoutMap)
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
        db.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid).collection("finishedWorkouts").document(workout.id).set(workoutMap)
    }

    fun autoSignIn() {
        firebaseAuth.currentUser?.let {
        } ?: run {
            firebaseAuth.signInWithEmailAndPassword("amir.makhmudov.f@gmail.com", "135791")
                .addOnCompleteListener {
                }
        }
    }
}