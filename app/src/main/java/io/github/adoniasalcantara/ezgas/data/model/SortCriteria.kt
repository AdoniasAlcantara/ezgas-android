package io.github.adoniasalcantara.ezgas.data.model

import androidx.annotation.IdRes
import io.github.adoniasalcantara.ezgas.R

enum class SortCriteria(val id: Int) {

    PRICE(R.id.sort_price),
    DISTANCE(R.id.sort_distance);

    companion object {
        fun fromId(@IdRes id: Int) = when (id) {
            R.id.sort_price -> PRICE
            R.id.sort_distance -> DISTANCE
            else -> throw IllegalArgumentException("Invalid given ID: $id")
        }
    }
}