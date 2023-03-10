package org.akozlenko.gitstars.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import org.akozlenko.gitstars.api.services.GithubApi
import org.akozlenko.gitstars.model.RepositoryInfo
import org.akozlenko.gitstars.utils.DeviceInfoProvider
import java.text.SimpleDateFormat
import java.util.*

class GithubRepository(
    private val apiService: GithubApi,
    private val deviceInfoProvider: DeviceInfoProvider,
) {

    private val timeZone: TimeZone
        get() = deviceInfoProvider.getTimeZone()

    fun searchLatestRepositories(
        pagingConfig: PagingConfig = DEFAULT_PAGING_CONFIG,
        fromDateInMs: Long
    ): LiveData<PagingData<RepositoryInfo>> {
        // Prepare query string
        val queryString = prepareDateQueryValue(
            currentTimeZone = timeZone,
            timestampInMs = fromDateInMs
        )
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                SearchPagingSource(
                    apiService = apiService,
                    query = queryString
                )
            }
        ).liveData
    }

    companion object {
        private val US_LOCALE = Locale.US
        private const val QUERY_DATE_FORMAT = "yyyy-MM-dd"

        private val DEFAULT_PAGING_CONFIG =  PagingConfig(
            pageSize = SearchPagingSource.PAGE_SIZE,
            enablePlaceholders = true
        )

        private val DATE_FORMATTER = SimpleDateFormat(
            QUERY_DATE_FORMAT,
            US_LOCALE
        )

        private fun prepareDateQueryValue(
            currentTimeZone: TimeZone,
            timestampInMs: Long
        ): String {
            val calendar = Calendar.getInstance().apply {
                timeZone = currentTimeZone
                timeInMillis = timestampInMs
            }
            // Prepare formatted date
            val formattedDate = DATE_FORMATTER.format(calendar.time)
            return "created:>$formattedDate"
        }

    }
}
