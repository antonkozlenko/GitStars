package org.akozlenko.gitstars.data

import androidx.paging.PagingSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.akozlenko.gitstars.api.model.RepositoryOwnerResponseData
import org.akozlenko.gitstars.api.model.RepositoryResponseData
import org.akozlenko.gitstars.api.model.SearchResultResponseData
import org.akozlenko.gitstars.api.services.GithubApi
import org.akozlenko.gitstars.model.RepositoryInfo
import org.akozlenko.gitstars.model.RepositoryOwnerIfo
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchPagingSourceTest {

    @MockK
    private lateinit var apiService: GithubApi

    private lateinit var pagingSource: SearchPagingSource

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockKAnnotations.init(this)

        pagingSource = SearchPagingSource(
            apiService = apiService,
            query = TEST_SEARCH_QUERY
        )
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        unmockkAll()
    }

    //region Success flows
    //region Initial load
    @Test
    @Throws(Exception::class)
    fun testInitialLoadSuccessHasMoreFlow() {
        runBlockingTest {
            val pageIndex = DEFAULT_PAGE_INDEX
            val input = prepareApiResponseData(
                resultsCount = PAGE_SIZE,
                page = pageIndex
            )

            prepareApiSuccessFlow(input)

            val outputData = convertApiDataToModel(input)

            val expected = PagingSource.LoadResult.Page(
                data = outputData,
                prevKey = null,
                nextKey = pageIndex.inc()
            )

            // Prepare load params
            val loadParams = PagingSource.LoadParams.Refresh(
                key = pageIndex,
                loadSize = PAGE_SIZE,
                placeholdersEnabled = true
            )

            // Load
            val actual = pagingSource.load(loadParams)

            assertEquals(expected, actual)

            // Verify API call
            coVerify {
                apiService.searchRepositories(
                    headers = any(),
                    queryString = TEST_SEARCH_QUERY,
                    sorting = any(),
                    order = any(),
                    pageSize = PAGE_SIZE,
                    page = pageIndex
                )
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun testInitialLoadSuccessNoResultsFlow() {
        runBlockingTest {
            val pageIndex = DEFAULT_PAGE_INDEX
            val input = prepareApiResponseData(
                resultsCount = 0,
                page = pageIndex
            )

            prepareApiSuccessFlow(input)

            val outputData = convertApiDataToModel(input)

            val expected = PagingSource.LoadResult.Page(
                data = outputData,
                prevKey = null,
                nextKey = null // nothing to load more
            )

            // Prepare load params
            val loadParams = PagingSource.LoadParams.Refresh(
                key = pageIndex,
                loadSize = PAGE_SIZE,
                placeholdersEnabled = true
            )

            // Load
            val actual = pagingSource.load(loadParams)

            assertEquals(expected, actual)

            // Verify API call
            coVerify {
                apiService.searchRepositories(
                    headers = any(),
                    queryString = TEST_SEARCH_QUERY,
                    sorting = any(),
                    order = any(),
                    pageSize = PAGE_SIZE,
                    page = pageIndex
                )
            }
        }
    }
    //endregion

    //region Append
    @Test
    @Throws(Exception::class)
    fun testNextLoadSuccessHasMoreFlow() {
        runBlockingTest {
            val pageIndex = 2
            val input = prepareApiResponseData(
                resultsCount = PAGE_SIZE,
                page = pageIndex
            )

            prepareApiSuccessFlow(input)

            val outputData = convertApiDataToModel(input)

            val expected = PagingSource.LoadResult.Page(
                data = outputData,
                prevKey = pageIndex.dec(),
                nextKey = pageIndex.inc()
            )

            // Prepare load params
            val loadParams = PagingSource.LoadParams.Append(
                key = pageIndex,
                loadSize = PAGE_SIZE,
                placeholdersEnabled = true
            )

            // Load
            val actual = pagingSource.load(loadParams)

            assertEquals(expected, actual)

            // Verify API call
            coVerify {
                apiService.searchRepositories(
                    headers = any(),
                    queryString = TEST_SEARCH_QUERY,
                    sorting = any(),
                    order = any(),
                    pageSize = PAGE_SIZE,
                    page = pageIndex
                )
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun testNextLoadSuccessNoResultsFlow() {
        runBlockingTest {
            val pageIndex = 2
            val input = prepareApiResponseData(
                resultsCount = 0,
                page = pageIndex
            )

            prepareApiSuccessFlow(input)

            val outputData = convertApiDataToModel(input)

            val expected = PagingSource.LoadResult.Page(
                data = outputData,
                prevKey = pageIndex.dec(),
                nextKey = null // nothing to load more
            )

            // Prepare load params
            val loadParams = PagingSource.LoadParams.Append(
                key = pageIndex,
                loadSize = PAGE_SIZE,
                placeholdersEnabled = true
            )

            // Load
            val actual = pagingSource.load(loadParams)

            assertEquals(expected, actual)

            // Verify API call
            coVerify {
                apiService.searchRepositories(
                    headers = any(),
                    queryString = TEST_SEARCH_QUERY,
                    sorting = any(),
                    order = any(),
                    pageSize = PAGE_SIZE,
                    page = pageIndex
                )
            }
        }
    }
    //endregion

    //region Prepend
    @Test
    @Throws(Exception::class)
    fun testPrevLoadSuccessFirstPageFlow() {
        runBlockingTest {
            val pageIndex = 0
            val input = prepareApiResponseData(
                resultsCount = PAGE_SIZE,
                page = pageIndex
            )

            prepareApiSuccessFlow(input)

            val outputData = convertApiDataToModel(input)

            val expected = PagingSource.LoadResult.Page(
                data = outputData,
                prevKey = null,
                nextKey = pageIndex.inc()
            )

            // Prepare load params
            val loadParams = PagingSource.LoadParams.Prepend(
                key = pageIndex,
                loadSize = PAGE_SIZE,
                placeholdersEnabled = true
            )

            // Load
            val actual = pagingSource.load(loadParams)

            assertEquals(expected, actual)

            // Verify API call
            coVerify {
                apiService.searchRepositories(
                    headers = any(),
                    queryString = TEST_SEARCH_QUERY,
                    sorting = any(),
                    order = any(),
                    pageSize = PAGE_SIZE,
                    page = pageIndex
                )
            }
        }
    }
    //endregion
    //endregion

    //region Failed flows
    @Test
    @Throws(Exception::class)
    fun testLoadFailedFlow() {
        runBlockingTest {
            val pageIndex = DEFAULT_PAGE_INDEX

            prepareApiErrorFlow()

            val expected = PagingSource.LoadResult.Error<Int, RepositoryInfo>(API_ERROR)

            // Prepare load params
            val loadParams = PagingSource.LoadParams.Refresh(
                key = pageIndex,
                loadSize = PAGE_SIZE,
                placeholdersEnabled = true
            )

            // Load
            val actual = pagingSource.load(loadParams)

            assertEquals(expected, actual)

            // Verify API call
            coVerify {
                apiService.searchRepositories(
                    headers = any(),
                    queryString = TEST_SEARCH_QUERY,
                    sorting = any(),
                    order = any(),
                    pageSize = PAGE_SIZE,
                    page = pageIndex
                )
            }
        }
    }
    //endregion

    //region Helpers
    private fun prepareApiSuccessFlow(
        responseData: SearchResultResponseData
    ) {
        coEvery {
            apiService.searchRepositories(
                headers = any(),
                queryString = any(),
                sorting = any(),
                order = any(),
                pageSize = any(),
                page = any()
            )
        } returns responseData
    }

    private fun prepareApiErrorFlow() {
        coEvery {
            apiService.searchRepositories(
                headers = any(),
                queryString = any(),
                sorting = any(),
                order = any(),
                pageSize = any(),
                page = any()
            )
        } throws API_ERROR
    }
    //endregion

    companion object {
        private const val DEFAULT_PAGE_INDEX = SearchPagingSource.DEFAULT_PAGE_INDEX
        private const val PAGE_SIZE = SearchPagingSource.PAGE_SIZE

        private const val TEST_SEARCH_QUERY = "created:>2022-12-30"

        private val API_ERROR = RuntimeException("Search failed")

        private val DUMMY_OWNER_DATA = RepositoryOwnerResponseData(
            username = "john_dou",
            avatarUrl = "https://abc.com/avatar.png"
        )
        private val DUMMY_OWNER = DUMMY_OWNER_DATA.run {
            RepositoryOwnerIfo(
                username = username,
                avatarUrl = avatarUrl
            )
        }

        private fun prepareApiResponseData(
            resultsCount: Int,
            page: Int
        ): SearchResultResponseData {
            val repositories = mutableListOf<RepositoryResponseData>()
            resultsCount.takeIf { it > 0 }?.dec()?.let { size ->
                for (i in 0..size) {
                    val repo = RepositoryResponseData(
                        id = page + i,
                        name = "Repo-$i",
                        description = "Description $i",
                        stars = size.inc() - i,
                        owner = DUMMY_OWNER_DATA
                    )
                    repositories.add(repo)
                }
            }
            return SearchResultResponseData(
                totalCount = resultsCount,
                incomplete = false,
                items = repositories
            )
        }

        private fun convertApiDataToModel(data: SearchResultResponseData): List<RepositoryInfo> {
            return data.items.map {
                RepositoryInfo(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    stars = it.stars,
                    owner = DUMMY_OWNER
                )
            }
        }

    }

}
