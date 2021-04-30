package io.github.adoniasalcantara.ezgas.di

import io.github.adoniasalcantara.ezgas.ui.details.DetailsViewModel
import io.github.adoniasalcantara.ezgas.ui.favorites.FavoritesViewModel
import io.github.adoniasalcantara.ezgas.ui.stations.StationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { StationsViewModel(repository = get(), settings = get()) }

    viewModel { FavoritesViewModel(repository = get(), settings = get()) }

    viewModel { (stationId: String) -> DetailsViewModel(stationId = stationId, repository = get()) }
}