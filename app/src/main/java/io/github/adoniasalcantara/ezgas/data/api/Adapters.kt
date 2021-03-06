@file:Suppress("unused")

package io.github.adoniasalcantara.ezgas.data.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BigDecimalAdapter {

    @FromJson
    fun fromJson(value: String) = BigDecimal(value)

    @ToJson
    fun toJson(value: BigDecimal) = value.toString()
}

class OffsetDateTimeAdapter {
    private val formatter = DateTimeFormatter.ISO_DATE

    @FromJson
    fun fromJson(value: String): LocalDate = LocalDate.parse(value, formatter)

    @ToJson
    fun toJson(value: LocalDate): String = value.format(formatter)
}