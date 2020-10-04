package io.github.adoniasalcantara.ezgas

import android.app.Application
import io.github.adoniasalcantara.ezgas.di.dataModule
import io.github.adoniasalcantara.ezgas.di.uiModule
import io.github.adoniasalcantara.ezgas.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class EzGasApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(dataModule, viewModelModule, uiModule)
        }
    }
}