package com.example.exerwise.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    val name: String = "",
    val muscle: String = "",
    val instructions: String = "",
    var sets: MutableList<ExerciseSet> = mutableListOf()
) : Parcelable