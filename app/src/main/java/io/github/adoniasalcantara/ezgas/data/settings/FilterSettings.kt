package io.github.adoniasalcantara.ezgas.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import io.github.adoniasalcantara.ezgas.data.model.Filter
import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.data.model.SortCriteria
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("filter_prefs")

class FilterSettings(context: Context) {
    private val dataStore = context.dataStore

    val filterFlow = dataStore.data.map { prefs ->
        Filter(
            fuelType = prefs[FUEL_TYPE]?.let(FuelType::valueOf)
                ?: FuelType.GASOLINE,

            sortCriteria = prefs[SORT_CRITERIA]?.let(SortCriteria::valueOf)
                ?: SortCriteria.PRICE,

            distance = prefs[DISTANCE] ?: 25f
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
        val FUEL_TYPE = stringPreferencesKey("fuel_type")
        val SORT_CRITERIA = stringPreferencesKey("sort_criteria")
        val DISTANCE = floatPreferencesKey("distance")
    }
}