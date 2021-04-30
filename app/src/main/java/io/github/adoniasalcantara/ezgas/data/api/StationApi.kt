package io.github.adoniasalcantara.ezgas.data.api

import io.github.adoniasalcantara.ezgas.data.model.FuelType
import io.github.adoniasalcantara.ezgas.data.model.SortCriteria
import io.github.adoniasalcantara.ezgas.data.model.Station
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StationApi {

    @GET("stations/{id}")
    suspend fun fetchById(@Path("id") id: String): Station

    @GET("stations")
    suspend fun fetchByIds(@Query("ids") ids: String): List<Station>

    @GET("stations/nearby")
    suspend fun searchNearby(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("distance") distance: Float,
        @Query("fuel") fuelType: FuelType,
        @Query("sort") sortCriteria: SortCriteria,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int
    ): NearbyResponse
}