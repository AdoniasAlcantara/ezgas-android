package io.github.adoniasalcantara.ezgas.util.location

import android.location.Location

sealed class LocationUpdate {

    data class Available(val location: Location) : LocationUpdate()

    object Unavailable : LocationUpdate()
}