package org.akozlenko.gitstars.di

import org.akozlenko.gitstars.data.GithubRepository
import org.koin.dsl.module

val repositoriesModule = module {

    single {
        GithubRepository(
            apiService = get(),
            deviceInfoProvider = get()
        )
    }

}
