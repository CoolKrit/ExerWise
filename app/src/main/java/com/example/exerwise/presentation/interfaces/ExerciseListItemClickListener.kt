package com.example.exerwise.presentation.interfaces

import com.example.exerwise.data.model.Exercise

interface ExerciseListItemClickListener {
    fun onItemClick(item: Exercise)
}