package io.github.adoniasalcantara.ezgas.util.location

import android.content.Context
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest

class LocationSettingsResolver(fragment: Fragment, private val locationRequest: LocationRequest) {

    private val launcher = fragment.registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) {
        /* Ignore result for now */
    }

    fun resolve(context: Context) {
        val settingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()

        LocationServices
            .getSettingsClient(context)
            .checkLocationSettings(settingsRequest)
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    val intentSender = exception.resolution.intentSender
                    val intentSenderRequest = IntentSenderRequest.Builder(intentSender).build()
                    launcher.launch(intentSenderRequest)
                }
            }
    }
}