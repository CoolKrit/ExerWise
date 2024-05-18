package com.example.exerwise.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Set(
    var setNumber: Int,
    var setWeight: Double,
    var setReps: Int
) : Parcelable {
    constructor() : this(0, 0.0, 0)
}