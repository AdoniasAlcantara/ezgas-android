package io.github.adoniasalcantara.ezgas.util.location

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.android.gms.location.*

class LocationLiveData(
    private val client: FusedLocationProviderClient,
    private val request: LocationRequest
) : LiveData<LocationUpdate>() {

    @SuppressLint("MissingPermission")
    override fun onActive() {
        Log.i(TAG, "Updates started")
        client.requestLocationUpdates(request, callback, null)
    }

    override fun onInactive() {
        Log.i(TAG, "Updates stopped")
        client.removeLocationUpdates(callback)
    }

    private val callback = object : LocationCallback() {

        override fun onLocationResult(result: LocationResult?) {
            val location = result?.lastLocation ?: return

            value = LocationUpdate.Available(location)
            Log.d(TAG, "Available lat: ${location.latitude} lng: ${location.longitude}")
        }

        override fun onLocationAvailability(availability: LocationAvailability?) {
            availability ?: return

            if (!availability.isLocationAvailable) {
                value = LocationUpdate.Unavailable
                Log.d(TAG, "Unavailable")
            }
        }
    }

    private companion object {
        val TAG = LocationLiveData::class.simpleName
    }
}