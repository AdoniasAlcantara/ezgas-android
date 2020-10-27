package io.github.adoniasalcantara.ezgas.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.data.model.Station
import io.github.adoniasalcantara.ezgas.data.repository.FavoriteResult
import io.github.adoniasalcantara.ezgas.data.repository.Resource
import io.github.adoniasalcantara.ezgas.data.repository.StationRepository
import io.github.adoniasalcantara.ezgas.data.settings.FilterSettings
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: StationRepository,
    private val settings: FilterSettings
) : ViewModel() {

    private val _fuelType = settings.filterFlow.map { it.fuelType }
    val fuelType = _fuelType.asLiveData()

    val favorites = repository.favoritesFlow
        .combine(_fuelType, ::sortResultByFuelPrice)
        .asLiveData()

    fun applyFuelType(fuelType: FuelType) {
        viewModelScope.launch {
            val newFilter = settings.filterFlow
                .first()
                .copy(fuelType = fuelType)

            settings.setFilter(newFilter)
        }
    }

    fun refresh() {
        viewModelScope.launch { repository.refreshFavorites() }
    }

    private fun sortResultByFuelPrice(
        result: FavoriteResult?,
        fuelType: FuelType
    ): FavoriteResult? {
        if (result !is Resource.Success) return result

        val selector = { station: Station ->
            station.fuels.find { it.type == fuelType }?.salePrice
        }

        val comparator = compareBy(nullsLast(), selector)
        val sorted = result.data.sortedWith(comparator)

        return Resource.Success(sorted)
    }
}