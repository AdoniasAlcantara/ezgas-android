package io.github.adoniasalcantara.ezgas.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import io.github.adoniasalcantara.ezgas.data.repository.StationRepository
import kotlinx.coroutines.launch

class DetailsViewModel(private val stationId: Int, private val repository: StationRepository) :
    ViewModel() {

    val fuels = liveData(viewModelScope.coroutineContext) {
        val fuels = repository.getById(stationId).fuels
        emit(fuels)
    }

    val isFavorite = liveData {
        emit(repository.isFavorite(stationId))
    }

    fun setFavorite(isFavorite: Boolean) = viewModelScope.launch {
        if (isFavorite) {
            repository.addFavorite(stationId)
        } else {
            repository.removeFavorite(stationId)
        }
    }
}