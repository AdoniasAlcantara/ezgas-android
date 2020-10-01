package io.github.adoniasalcantara.ezgas.data.api.response

data class StationResponse(
    val id: Int,
    val company: String,
    val brandId: Int,
    val brandName: String,
    val latitude: Double,
    val longitude: Double,
    val address: String?,
    val city: String,
    val state: String,
    val distance: Float?,
    val gasoline: FuelResponse?,
    val ethanol: FuelResponse?,
    val diesel: FuelResponse?,
    val dieselS10: FuelResponse?
)