package com.example.exerwise

import com.example.exerwise.data.model.Workout

interface WorkoutItemClickListener {
    fun onWorkoutItemClick(workout: Workout)
}