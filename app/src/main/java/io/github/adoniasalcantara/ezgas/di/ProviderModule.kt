package io.github.adoniasalcantara.ezgas.di

import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import io.github.adoniasalcantara.ezgas.util.location.LocationLiveData
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val providerModule = module {
    single {
        LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
            .setInterval(10000)         // 10 seg
            .setFastestInterval(5000)   // 5 seg
    }

    single {
        LocationLiveData(
            client = LocationServices.getFusedLocationProviderClient(androidContext()),
            request = get()
        )
    }
}