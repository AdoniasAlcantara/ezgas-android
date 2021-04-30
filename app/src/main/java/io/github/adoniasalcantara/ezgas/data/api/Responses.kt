package io.github.adoniasalcantara.ezgas.data.api

import io.github.adoniasalcantara.ezgas.data.model.Station

data class NearbyResponse(
    val number: Int,
    val size: Int,
    val totalElements: Int,
    val last: Boolean,
    val content: List<Station>
)