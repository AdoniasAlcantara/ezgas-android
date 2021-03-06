package io.github.adoniasalcantara.ezgas.di

import io.github.adoniasalcantara.ezgas.data.api.ApiFactory
import io.github.adoniasalcantara.ezgas.data.database.Database
import io.github.adoniasalcantara.ezgas.data.repository.StationRepository
import io.github.adoniasalcantara.ezgas.data.repository.StationRepositoryImpl
import io.github.adoniasalcantara.ezgas.data.settings.FilterSettings
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { FilterSettings(androidContext()) }

    single<StationRepository> {
        StationRepositoryImpl(
            apiFactory = ApiFactory(androidContext()),
            favoriteDao = Database(androidContext())
        )
    }
}