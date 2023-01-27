package org.akozlenko.gitstars.api.model

import com.google.gson.annotations.SerializedName

data class RepositoryResponseData(
    val id: Int,
    val name: String,
    val description: String?,
    @SerializedName("stargazers_count")
    val stars: Int,
    val owner: RepositoryOwnerResponseData
)
