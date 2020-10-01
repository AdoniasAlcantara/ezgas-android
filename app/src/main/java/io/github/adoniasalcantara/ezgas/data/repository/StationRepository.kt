package io.github.adoniasalcantara.ezgas.data.repository

import androidx.paging.PagingData
import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.data.model.SortCriteria
import io.github.adoniasalcantara.ezgas.data.model.Station
import kotlinx.coroutines.flow.Flow

typealias NearbyResult = PagingData<Station>

data class NearbyQuery(
    val latitude: Double,
    val longitude: Double,
    val distance: Float,
    val fuelType: FuelType,
    val sortCriteria: SortCriteria
)

interface StationRepository {

    fun searchNearby(query: NearbyQuery): Flow<NearbyResult>
}