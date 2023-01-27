package org.akozlenko.gitstars.di

import org.akozlenko.gitstars.utils.DefaultDispatcherProvider
import org.akozlenko.gitstars.utils.DispatcherProvider
import org.koin.dsl.module

val utilsModule = module {
    single<DispatcherProvider> {
        DefaultDispatcherProvider()
    }
}
