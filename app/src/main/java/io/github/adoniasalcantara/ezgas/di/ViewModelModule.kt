package io.github.adoniasalcantara.ezgas.di

import io.github.adoniasalcantara.ezgas.ui.stations.StationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { StationsViewModel(settings = get()) }
}