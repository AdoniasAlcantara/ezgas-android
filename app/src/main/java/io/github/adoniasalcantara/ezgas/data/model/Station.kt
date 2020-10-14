package io.github.adoniasalcantara.ezgas.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Station(
    val id: Int,
    val company: String,
    val brand: Brand,
    val place: Place,
    val fuels: Set<Fuel>
) : Parcelable