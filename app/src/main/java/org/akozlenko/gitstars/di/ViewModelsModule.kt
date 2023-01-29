package org.akozlenko.gitstars.di

import org.akozlenko.gitstars.ui.search.SearchRepositoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel {
        SearchRepositoriesViewModel(
            repository = get(),
            deviceInfoProvider = get()
        )
    }

}
