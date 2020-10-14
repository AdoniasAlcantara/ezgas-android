package io.github.adoniasalcantara.ezgas.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place(
    val latitude: Double,
    val longitude: Double,
    val distance: Float?,
    val address: String?,
    val city: String,
    val state: String
) : Parcelable