package org.akozlenko.gitstars.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.akozlenko.gitstars.data.GithubRepository
import org.akozlenko.gitstars.model.RepositoryInfo
import org.akozlenko.gitstars.utils.DeviceInfoProvider
import org.akozlenko.gitstars.utils.DispatcherProvider
import java.util.concurrent.TimeUnit

class SearchRepositoriesViewModel(
    private val repository: GithubRepository,
    private val deviceInfoProvider: DeviceInfoProvider,
    private val disaptcherProvider: DispatcherProvider
) : ViewModel() {

    private val currentTimeInMs: Long
        get() = deviceInfoProvider.getCurrentTimeInMs()

    private val listData = MutableLiveData<List<RepositoryInfo>>()
    val list: LiveData<List<RepositoryInfo>>
        get() = listData

    fun loadPageContent(): LiveData<PagingData<RepositoryInfo>> {
        val searchFromDate = prepareSearchFromDate(currentTimeInMs)
        return repository.searchLatestRepositories(
            fromDateInMs = searchFromDate
        ).cachedIn(viewModelScope)
    }

    fun loadPageContent2() {
        viewModelScope.launch {
            try {
                val searchFromDate = prepareSearchFromDate(currentTimeInMs)
                val results = withContext(disaptcherProvider.io()) {
                    repository.searchLatestRepositories2(
                        fromDateInMs = searchFromDate
                    )
                }
                listData.value = results
            } catch (e: Throwable) {
                Log.e("SearchVM", "Failed -> ${e.message}")
            }
        }
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
