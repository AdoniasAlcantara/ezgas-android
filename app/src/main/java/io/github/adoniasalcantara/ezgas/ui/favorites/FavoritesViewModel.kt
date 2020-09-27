package io.github.adoniasalcantara.ezgas.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.data.settings.FilterSettings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FavoritesViewModel(private val settings: FilterSettings) : ViewModel() {

    val fuelType = settings.filterFlow.map { it.fuelType }

    fun applyFuelType(fuelType: FuelType) {
        viewModelScope.launch {
            val newFilter = settings.filterFlow
                .first()
                .copy(fuelType = fuelType)

            settings.setFilter(newFilter)
        }
    }
}