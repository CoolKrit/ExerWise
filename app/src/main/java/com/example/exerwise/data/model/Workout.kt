package com.example.exerwise.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Workout(
    var id: String,
    var name: String,
    var duration: String,
    var volume: String,
    var sets: String,
    var exercises: MutableList<Exercise>
) : Parcelable {
    constructor() : this("", "", "", "", "", mutableListOf())
}