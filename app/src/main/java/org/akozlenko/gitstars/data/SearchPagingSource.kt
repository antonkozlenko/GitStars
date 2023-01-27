package org.akozlenko.gitstars.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.akozlenko.gitstars.api.model.RepositoryOwnerResponseData
import org.akozlenko.gitstars.api.model.RepositoryResponseData
import org.akozlenko.gitstars.api.services.GithubApi
import org.akozlenko.gitstars.model.RepositoryInfo
import org.akozlenko.gitstars.model.RepositoryOwnerIfo

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
                page = page
            ).items.map {
                convertRepoDataToModel(it)
            }
            // Prepare result
            LoadResult.Page(
                data = searchResult,
                prevKey = if (page == DEFAULT_PAGE_INDEX)
                    null
                else
                    page - 1,
                nextKey = if (searchResult.isEmpty())
                    null
                else
                    page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(
        state: PagingState<Int, RepositoryInfo>
    ): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val DEFAULT_PAGE_INDEX = 0

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
