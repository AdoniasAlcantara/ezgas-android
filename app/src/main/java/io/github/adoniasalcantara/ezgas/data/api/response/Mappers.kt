package io.github.adoniasalcantara.ezgas.data.api.response

import io.github.adoniasalcantara.ezgas.data.model.*

fun StationResponse.toStation(): Station {
    val brand = Brand(brandId, brandName)

    val place = Place(
        latitude,
        longitude,
        distance,
        address,
        city,
        state
    )

    val fuels = listOfNotNull(
        gasoline?.toFuel(FuelType.GASOLINE),
        ethanol?.toFuel(FuelType.ETHANOL),
        diesel?.toFuel(FuelType.DIESEL),
        dieselS10?.toFuel(FuelType.DIESEL_S10)
    ).toSet()

    return Station(id, company, brand, place, fuels)
}

fun FuelResponse.toFuel(fuelType: FuelType) = Fuel(
    fuelType,
    salePrice,
    purchasePrice,
    updated,
    source
)

fun List<StationResponse>.toStations() = this.map { it.toStation() }