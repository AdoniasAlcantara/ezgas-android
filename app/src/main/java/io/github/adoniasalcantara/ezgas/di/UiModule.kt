package io.github.adoniasalcantara.ezgas.di

import io.github.adoniasalcantara.ezgas.ui.favorites.FavoritesAdapter
import io.github.adoniasalcantara.ezgas.ui.stations.StationsAdapter
import org.koin.dsl.module

val uiModule = module {
    factory { StationsAdapter(assets = get()) }

    factory { FavoritesAdapter(assets = get()) }
}