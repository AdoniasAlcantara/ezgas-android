package io.github.adoniasalcantara.ezgas.data.settings

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import io.github.adoniasalcantara.ezgas.data.model.Filter
import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.data.model.SortCriteria
import kotlinx.coroutines.flow.map

class FilterSettings(context: Context) {

    private val dataStore = context.createDataStore("filter_prefs")

    val filterFlow = dataStore.data.map { prefs ->
        Filter(
            fuelType = prefs[FUEL_TYPE]?.let(FuelType::valueOf)
                ?: FuelType.GASOLINE,

            sortCriteria = prefs[SORT_CRITERIA]?.let(SortCriteria::valueOf)
                ?: SortCriteria.BY_PRICE,

            distance = prefs[DISTANCE] ?: 25_000f
        )
    }

    suspend fun setFilter(filter: Filter) {
        dataStore.edit { prefs ->
            prefs[FUEL_TYPE] = filter.fuelType.name
            prefs[SORT_CRITERIA] = filter.sortCriteria.name
            prefs[DISTANCE] = filter.distance
        }
    }

    private companion object {
        val FUEL_TYPE = preferencesKey<String>("fuel_type")
        val SORT_CRITERIA = preferencesKey<String>("sort_criteria")
        val DISTANCE = preferencesKey<Float>("distance")
    }
}