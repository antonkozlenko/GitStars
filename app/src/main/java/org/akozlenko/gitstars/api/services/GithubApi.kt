package org.akozlenko.gitstars.api.services

import org.akozlenko.gitstars.api.model.SearchResultResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET(URL_SEARCH)
    suspend fun searchRepositories(
        @Query("q") queryString: String,
        @Query("sort") sorting: String = PARAM_SORT,
        @Query("order") order: String = PARAM_ORDER,
        @Query("page") page: Int? = null
    ): Response<SearchResultResponseData>

    companion object {
        private const val URL_SEARCH = "search/repositories"

        private const val PARAM_SORT = "stars"
        private const val PARAM_ORDER = "desc"
    }

}
