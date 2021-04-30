package io.github.adoniasalcantara.ezgas.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Station(
    val id: String,
    val company: String,
    val brand: Brand,
    val place: Place,
    val fuels: Map<FuelType, Fuel>
) : Parcelable