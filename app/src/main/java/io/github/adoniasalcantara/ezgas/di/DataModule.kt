package io.github.adoniasalcantara.ezgas.di

import io.github.adoniasalcantara.ezgas.data.settings.FilterSettings
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { FilterSettings(androidContext()) }
}