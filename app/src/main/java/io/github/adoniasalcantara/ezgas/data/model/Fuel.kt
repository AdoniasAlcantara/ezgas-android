package io.github.adoniasalcantara.ezgas.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
import java.time.LocalDate

@Parcelize
data class Fuel(
    val updatedAt: LocalDate,
    val price: BigDecimal
) : Parcelable