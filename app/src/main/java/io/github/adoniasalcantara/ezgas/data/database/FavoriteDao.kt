package io.github.adoniasalcantara.ezgas.data.database

interface FavoriteDao {

    fun findAll(): Set<Int>

    fun add(stationId: Int)

    fun remove(stationId: Int)
}
