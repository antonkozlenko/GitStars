package org.akozlenko.gitstars

import androidx.multidex.MultiDexApplication
import org.akozlenko.gitstars.di.apiModule
import org.akozlenko.gitstars.di.utilsModule
import org.akozlenko.gitstars.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GitStarsApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        startKoin {
            androidContext(applicationContext)
            // Modules
            modules(
                apiModule,
                utilsModule,
                viewModelsModule
            )
        }
    }
}
