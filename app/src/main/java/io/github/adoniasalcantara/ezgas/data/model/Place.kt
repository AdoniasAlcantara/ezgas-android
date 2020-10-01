package io.github.adoniasalcantara.ezgas.data.model

data class Place(
    val latitude: Double,
    val longitude: Double,
    val distance: Float?,
    val address: String?,
    val city: String,
    val state: String
)