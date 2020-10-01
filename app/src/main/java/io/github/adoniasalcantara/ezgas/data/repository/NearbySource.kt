package io.github.adoniasalcantara.ezgas.data.repository

import androidx.paging.PagingSource
import io.github.adoniasalcantara.ezgas.data.api.StationApi
import io.github.adoniasalcantara.ezgas.data.api.response.toStations
import io.github.adoniasalcantara.ezgas.data.model.Station

class NearbySource(private val api: StationApi, private val query: NearbyQuery) :
    PagingSource<Int, Station>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Station> = try {
        val pageNumber = params.key ?: 0
        val pageSize = params.loadSize

        val response = query.run {
            api.searchNearby(
                latitude,
                longitude,
                distance,
                fuelType,
                sortCriteria,
                pageNumber,
                pageSize
            )
        }

        val stations = response.content.toStations()
        val nextPage = if (response.last) null else pageNumber + 1

        LoadResult.Page(
            data = stations,
            prevKey = null,
            nextKey = nextPage
        )
    } catch (throwable: Throwable) {
        LoadResult.Error(throwable)
    }
}