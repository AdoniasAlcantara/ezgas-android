package io.github.adoniasalcantara.ezgas.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.github.adoniasalcantara.ezgas.data.api.ApiFactory
import io.github.adoniasalcantara.ezgas.data.database.FavoriteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StationRepositoryImpl(apiFactory: ApiFactory, private val favoriteDao: FavoriteDao) :
    StationRepository {

    private val api by lazy { apiFactory.stationApi() }

    private val _favoritesFlow = MutableStateFlow<FavoriteResult?>(null)
    override val favoritesFlow: StateFlow<FavoriteResult?> = _favoritesFlow

    init {
        CoroutineScope(Dispatchers.IO).launch {
            refreshFavorites()
        }
    }

    override suspend fun getById(id: String) = withContext(Dispatchers.IO) {
        api.fetchById(id)
    }

    override fun searchNearby(query: NearbyQuery): Flow<NearbyResult> {
        val config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
            enablePlaceholders = false,
        )

        return Pager(config) { NearbySource(api, query) }.flow
    }

    override suspend fun addFavorite(stationId: String) = withContext(Dispatchers.IO) {
        favoriteDao.add(stationId)
        refreshFavorites()
    }

    override suspend fun removeFavorite(stationId: String) = withContext(Dispatchers.IO) {
        favoriteDao.remove(stationId)
        refreshFavorites()
    }

    override suspend fun isFavorite(stationId: String): Boolean = withContext(Dispatchers.IO) {
        stationId in favoriteDao.findAll()
    }

    override suspend fun refreshFavorites() {
        val stationIds = favoriteDao.findAll()

        if (stationIds.isEmpty()) {
            _favoritesFlow.value = null
        } else {
            _favoritesFlow.value = Resource.Loading()

            try {
                val stations = api.fetchByIds(stationIds.joinToString(","))
                _favoritesFlow.value = Resource.Success(stations)
            } catch (error: Throwable) {
                _favoritesFlow.value = Resource.Error(error)
            }
        }
    }
}