package io.github.adoniasalcantara.ezgas.data.model

import androidx.annotation.IdRes
import io.github.adoniasalcantara.ezgas.R

enum class FuelType(val id: Int, val text: Int) {

    GASOLINE(R.id.gasoline, R.string.fuel_gasoline),
    ETHANOL(R.id.ethanol, R.string.fuel_ethanol),
    DIESEL(R.id.diesel, R.string.fuel_diesel),
    DIESEL_S10(R.id.diesel_s10, R.string.fuel_diesel_s10);

    companion object {
        fun fromId(@IdRes id: Int) = when (id) {
            R.id.gasoline -> GASOLINE
            R.id.ethanol -> ETHANOL
            R.id.diesel -> DIESEL
            R.id.diesel_s10 -> DIESEL_S10
            else -> throw IllegalArgumentException("Invalid given ID: $id")
        }
    }
}