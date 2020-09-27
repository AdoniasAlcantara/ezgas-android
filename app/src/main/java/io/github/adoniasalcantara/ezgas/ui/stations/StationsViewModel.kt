package io.github.adoniasalcantara.ezgas.ui.stations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.adoniasalcantara.ezgas.data.model.Filter
import io.github.adoniasalcantara.ezgas.data.settings.FilterSettings
import kotlinx.coroutines.launch

class StationsViewModel(private val settings: FilterSettings) : ViewModel() {

    val filter = settings.filterFlow

    fun applyFilter(filter: Filter) {
        viewModelScope.launch {
            settings.setFilter(filter)
        }
    }
}