package io.github.adoniasalcantara.ezgas.data.model

data class Filter(
    val fuelType: FuelType,
    val sortCriteria: SortCriteria,
    val distance: Float
)