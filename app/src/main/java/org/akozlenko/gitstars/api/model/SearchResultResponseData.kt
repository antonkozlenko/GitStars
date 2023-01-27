package org.akozlenko.gitstars.api.model

import com.google.gson.annotations.SerializedName

data class SearchResultResponseData(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incomplete: Boolean,
    val items: List<RepositoryResponseData>
)
