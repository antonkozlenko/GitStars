package org.akozlenko.gitstars.di

import org.akozlenko.gitstars.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel {
        MainViewModel(
            dispatcherProvider = get()
        )
    }

}
