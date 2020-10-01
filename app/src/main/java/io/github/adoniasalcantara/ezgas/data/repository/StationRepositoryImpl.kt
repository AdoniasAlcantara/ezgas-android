package io.github.adoniasalcantara.ezgas.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.github.adoniasalcantara.ezgas.data.api.ApiFactory
import kotlinx.coroutines.flow.Flow

class StationRepositoryImpl(apiFactory: ApiFactory) : StationRepository {

    private val api by lazy { apiFactory.stationApi() }

    override fun searchNearby(query: NearbyQuery): Flow<NearbyResult> {
        val config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
            enablePlaceholders = false,
        )

        return Pager(config) { NearbySource(api, query) }.flow
    }
}