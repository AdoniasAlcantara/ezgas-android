package io.github.adoniasalcantara.ezgas.data.model

data class Station(
    val id: Int,
    val company: String,
    val brand: Brand,
    val place: Place,
    val fuels: Set<Fuel>
)