package org.akozlenko.gitstars.model

data class RepositoryInfo(
    val id: Int,
    val name: String,
    val description: String?,
    val stars: Int,
    val owner: RepositoryOwnerIfo
)
