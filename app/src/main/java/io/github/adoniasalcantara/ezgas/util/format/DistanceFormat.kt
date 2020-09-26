package io.github.adoniasalcantara.ezgas.util.format

import java.text.NumberFormat

private val kilometerFormat = NumberFormat.getNumberInstance().apply {
    maximumFractionDigits = 1
    minimumFractionDigits = 1
}

fun Float.formatToKilometers() = kilometerFormat.format(this) + "Km"

fun Int.formatToKilometers() = "${this}Km"