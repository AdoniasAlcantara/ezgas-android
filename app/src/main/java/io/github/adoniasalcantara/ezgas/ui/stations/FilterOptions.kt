package io.github.adoniasalcantara.ezgas.ui.stations

import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.data.model.SortCriteria

data class FilterOptions(
    val fuelType: FuelType,
    val sortCriteria: SortCriteria,
    val distance: Float
)