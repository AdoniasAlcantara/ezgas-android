package io.github.adoniasalcantara.ezgas.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.github.adoniasalcantara.ezgas.data.api.ApiFactory
import io.github.adoniasalcantara.ezgas.data.api.response.toStation
import io.github.adoniasalcantara.ezgas.data.database.FavoriteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class StationRepositoryImpl(apiFactory: ApiFactory, private val favoriteDao: FavoriteDao) :
    StationRepository {

    private val api by lazy { apiFactory.stationApi() }

    override suspend fun getById(id: Int) = withContext(Dispatchers.IO) {
        api.fetchById(id).toStation()
    }

    override fun searchNearby(query: NearbyQuery): Flow<NearbyResult> {
        val config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
            enablePlaceholders = false,
        )

        return Pager(config) { NearbySource(api, query) }.flow
    }

    override suspend fun addFavorite(stationId: Int) = withContext(Dispatchers.IO) {
        favoriteDao.add(stationId)
    }

    override suspend fun removeFavorite(stationId: Int) = withContext(Dispatchers.IO) {
        favoriteDao.remove(stationId)
    }

    override suspend fun isFavorite(stationId: Int): Boolean = withContext(Dispatchers.IO) {
        stationId in favoriteDao.findAll()
    }
}