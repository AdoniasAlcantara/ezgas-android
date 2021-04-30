package io.github.adoniasalcantara.ezgas.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

// Consider using Room instead
class Database(context: Context) :
    SQLiteOpenHelper(context, DATABASE, null, VERSION),
    FavoriteDao {

    override fun findAll(): Set<String> {
        val cursor = readableDatabase.query(TBL_FAVORITE, null, null, null, null, null, null, null)
        val stationIds = mutableSetOf<String>()

        while (cursor.moveToNext()) {
            stationIds.add(cursor.getString(0))
        }

        cursor.close()

        return stationIds
    }

    override fun add(stationId: String) {
        val values = contentValuesOf(COL_STATION_ID to stationId)
        writableDatabase.insert(TBL_FAVORITE, null, values)
    }

    override fun remove(stationId: String) {
        val where = "$COL_STATION_ID = ?"
        writableDatabase.delete(TBL_FAVORITE, where, arrayOf(stationId))
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TBL_FAVORITE($COL_STATION_ID CHARACTER(24) PRIMARY KEY)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Do nothing
    }

    private companion object {
        const val VERSION = 1
        const val DATABASE = "EzGas"

        const val TBL_FAVORITE = "Favorite"
        const val COL_STATION_ID = "stationId"
    }
}