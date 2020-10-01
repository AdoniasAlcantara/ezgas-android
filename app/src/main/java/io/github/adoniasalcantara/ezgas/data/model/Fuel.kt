package io.github.adoniasalcantara.ezgas.data.model

import java.math.BigDecimal
import java.time.OffsetDateTime

data class Fuel(
    val type: FuelType,
    val salePrice: BigDecimal,
    val purchasePrice: BigDecimal?,
    val updatedAt: OffsetDateTime,
    val updatedBy: String
)