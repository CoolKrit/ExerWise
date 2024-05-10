package com.example.exerwise.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseSet(
    var setNumber: Int = 0,
    var weight: String = "",
    var reps: String = ""
) : Parcelable