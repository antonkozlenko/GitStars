package org.akozlenko.gitstars.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.akozlenko.gitstars.api.model.RepositoryOwnerResponseData
import org.akozlenko.gitstars.api.model.RepositoryResponseData
import org.akozlenko.gitstars.api.services.GithubApi
import org.akozlenko.gitstars.model.RepositoryInfo
import org.akozlenko.gitstars.model.RepositoryOwnerIfo
import kotlin.math.roundToInt

class SearchPagingSource(
    private val apiService: GithubApi,
    private val query: String
) : PagingSource<Int, RepositoryInfo>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, RepositoryInfo> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val searchResult = apiService.searchRepositories(
                queryString = query,
                pageSize = PAGE_SIZE,
                page = page
            )
            val repositories = searchResult.items.map {
                convertRepoDataToModel(it)
            }
            // Prepare result
            LoadResult.Page(
                data = repositories,
                prevKey = if (page == DEFAULT_PAGE_INDEX)
                    null
                else
                    page.dec(),
                nextKey = if (repositories.isEmpty())
                    null
                else
                    page.inc()
            )
        } catch (error: Throwable) {
            return LoadResult.Error(error)
        }
    }

    override fun getRefreshKey(
        state: PagingState<Int, RepositoryInfo>
    ): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.inc()
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.dec()
        }
    }

    companion object {
        const val DEFAULT_PAGE_INDEX = 0
        const val PAGE_SIZE = 100

        private fun convertRepoDataToModel(
            data: RepositoryResponseData
        ): RepositoryInfo {
            return data.run {
                val ownerValue = convertOwnerDataToModel(owner)
                RepositoryInfo(
                    id = id,
                    name = name,
                    description = description,
                    stars = stars,
                    owner = ownerValue
                )
            }
        }

        private fun convertOwnerDataToModel(
            data: RepositoryOwnerResponseData
        ): RepositoryOwnerIfo {
            return data.run {
                RepositoryOwnerIfo(
                    username = username,
                    avatarUrl = avatarUrl
                )
            }
        }
    }
}
