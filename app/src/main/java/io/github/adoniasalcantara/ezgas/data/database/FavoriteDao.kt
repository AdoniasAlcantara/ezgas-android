package io.github.adoniasalcantara.ezgas.data.database

interface FavoriteDao {

    fun findAll(): Set<String>

    fun add(stationId: String)

    fun remove(stationId: String)
}
