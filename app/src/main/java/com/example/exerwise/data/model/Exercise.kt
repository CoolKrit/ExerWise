package com.example.exerwise.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    val id: Int,
    val name: String,
    val bodyPart: String,
    val equipment: String,
    val target: String,
    val gifUrl: String,
    val instructions: List<String>,
    val exerciseSetsList: MutableList<Set>
) : Parcelable {
    constructor() : this(0, "", "", "", "", "", emptyList(), mutableListOf())
}