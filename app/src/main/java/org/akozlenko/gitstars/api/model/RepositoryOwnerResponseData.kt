package org.akozlenko.gitstars.api.model

import com.google.gson.annotations.SerializedName

data class RepositoryOwnerResponseData(
    @SerializedName("login")
    val username: String,
    @SerializedName("avatar_url")
    val avatarUrl: String?
)
