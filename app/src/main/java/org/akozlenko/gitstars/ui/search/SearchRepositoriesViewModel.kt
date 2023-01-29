package org.akozlenko.gitstars.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import org.akozlenko.gitstars.data.GithubRepository
import org.akozlenko.gitstars.model.RepositoryInfo
import org.akozlenko.gitstars.utils.DeviceInfoProvider
import java.util.concurrent.TimeUnit

class SearchRepositoriesViewModel(
    private val repository: GithubRepository,
    private val deviceInfoProvider: DeviceInfoProvider
) : ViewModel() {

    private val currentTimeInMs: Long
        get() = deviceInfoProvider.getCurrentTimeInMs()

    fun loadPageContent(): LiveData<PagingData<RepositoryInfo>> {
        val searchFromDate = prepareSearchFromDate(currentTimeInMs)
        return repository.searchLatestRepositories(
            fromDateInMs = searchFromDate
        ).cachedIn(viewModelScope)
    }

    companion object {
        private const val FROM_DATE_THRESHOLD_IN_DAYS = 30L
        private val FROM_DATE_THRESHOLD_IN_MS = TimeUnit.DAYS.toMillis(FROM_DATE_THRESHOLD_IN_DAYS)

        private fun prepareSearchFromDate(
            currentTimeInMs: Long
        ): Long {
            return currentTimeInMs - FROM_DATE_THRESHOLD_IN_MS
        }
    }

}
