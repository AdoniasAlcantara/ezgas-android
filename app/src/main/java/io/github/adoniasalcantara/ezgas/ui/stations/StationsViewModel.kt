package io.github.adoniasalcantara.ezgas.ui.stations

import android.location.Location
import androidx.lifecycle.*
import androidx.paging.cachedIn
import io.github.adoniasalcantara.ezgas.data.model.Filter
import io.github.adoniasalcantara.ezgas.data.repository.NearbyQuery
import io.github.adoniasalcantara.ezgas.data.repository.NearbyResult
import io.github.adoniasalcantara.ezgas.data.repository.StationRepository
import io.github.adoniasalcantara.ezgas.data.settings.FilterSettings
import io.github.adoniasalcantara.ezgas.util.combineSwitchMap
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class StationsViewModel(
    private val repository: StationRepository,
    private val settings: FilterSettings
) : ViewModel() {

    private var lastJob: Job? = null

    private val _isLocationPending = MutableLiveData(true)
    val isLocationPending: LiveData<Boolean> = _isLocationPending

    private val location = MutableLiveData<Location>()

    val filter = settings.filterFlow.asLiveData()

    val stations = combineSwitchMap(location, filter, ::doSearch)

    fun applyFilter(filter: Filter) {
        viewModelScope.launch { settings.setFilter(filter) }
    }

    fun searchNearbyStations(location: Location) {
        _isLocationPending.value = false
        this.location.value = location
    }

    fun notifyPendingLocation() {
        _isLocationPending.value = true
    }

    private fun doSearch(location: Location, filter: Filter): LiveData<NearbyResult> {
        val currentJob = Job()

        // Make sure that the last job is canceled before start a new one
        lastJob?.cancel()
        lastJob = currentJob

        val query = NearbyQuery(
            location.latitude,
            location.longitude,
            filter.distance,
            filter.fuelType,
            filter.sortCriteria
        )

        return repository.searchNearby(query)
            .cachedIn(viewModelScope + currentJob)
            .asLiveData()
    }
}