package io.github.adoniasalcantara.ezgas.data.api.response

import java.math.BigDecimal
import java.time.OffsetDateTime

data class FuelResponse(
    val salePrice: BigDecimal,
    val purchasePrice: BigDecimal?,
    val updated: OffsetDateTime,
    val source: String
)