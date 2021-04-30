package io.github.adoniasalcantara.ezgas.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place(
    val houseNumber: String?,
    val street: String?,
    val neighborhood: String?,
    val city: String,
    val state: String,
    val position: Position,
    val distance: Float?
) : Parcelable {

    fun shortAddress() = arrayOf(
        houseNumber,
        street,
        neighborhood
    ).filterNotNull().joinToString()

    fun fullAddress() = "${shortAddress()}, $city, $state"
}