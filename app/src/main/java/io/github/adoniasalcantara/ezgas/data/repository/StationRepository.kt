package io.github.adoniasalcantara.ezgas.data.repository

import androidx.paging.PagingData
import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.data.model.SortCriteria
import io.github.adoniasalcantara.ezgas.data.model.Station
import kotlinx.coroutines.flow.Flow

typealias NearbyResult = PagingData<Station>

typealias FavoriteResult = Resource<List<Station>>

data class NearbyQuery(
    val latitude: Double,
    val longitude: Double,
    val distance: Float,
    val fuelType: FuelType,
    val sortCriteria: SortCriteria
)

interface StationRepository {

    val favoritesFlow: Flow<FavoriteResult?>

    suspend fun getById(id: String): Station

    fun searchNearby(query: NearbyQuery): Flow<NearbyResult>

    suspend fun addFavorite(stationId: String)

    suspend fun removeFavorite(stationId: String)

    suspend fun isFavorite(stationId: String): Boolean

    suspend fun refreshFavorites()
}