package com.example.exerwise.presentation.interfaces

import com.example.exerwise.data.model.Workout

interface WorkoutItemClickListener {
    fun onWorkoutItemClick(workout: Workout)
}