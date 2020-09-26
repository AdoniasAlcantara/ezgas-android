package io.github.adoniasalcantara.ezgas.data.model

import androidx.annotation.IdRes
import io.github.adoniasalcantara.ezgas.R

enum class SortCriteria(val id: Int) {

    BY_PRICE(R.id.sort_price),
    BY_DISTANCE(R.id.sort_distance);

    companion object {
        fun fromId(@IdRes id: Int) = when (id) {
            R.id.sort_price -> BY_PRICE
            R.id.sort_distance -> BY_DISTANCE
            else -> throw IllegalArgumentException("Invalid given ID: $id")
        }
    }
}