package io.github.adoniasalcantara.ezgas.data.api.response

data class NearbyResponse(
    val number: Int,
    val size: Int,
    val totalElements: Int,
    val last: Boolean,
    val content: List<StationResponse>
)