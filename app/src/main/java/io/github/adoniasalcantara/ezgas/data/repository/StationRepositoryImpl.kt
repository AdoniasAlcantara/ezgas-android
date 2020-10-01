package io.github.adoniasalcantara.ezgas.data.repository

import kotlinx.coroutines.flow.Flow

class StationRepositoryImpl : StationRepository {

    override fun searchNearby(query: NearbyQuery): Flow<NearbyResult> {
        TODO("Not yet implemented")
    }
}