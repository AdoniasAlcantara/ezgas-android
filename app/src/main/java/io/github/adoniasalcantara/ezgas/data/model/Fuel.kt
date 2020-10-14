package io.github.adoniasalcantara.ezgas.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
import java.time.OffsetDateTime

@Parcelize
data class Fuel(
    val type: FuelType,
    val salePrice: BigDecimal,
    val purchasePrice: BigDecimal?,
    val updatedAt: OffsetDateTime,
    val updatedBy: String
) : Parcelable