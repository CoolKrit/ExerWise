package com.example.exerwise.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Workout(
    val id: String = "",
    val name: String = "",
    val exercises: MutableList<Exercise> = mutableListOf()
) : Parcelable