package models

import java.io.Serializable

data class RepoModel(
    val name: String,
    val language: String,
    val watchers: Int,
    val description: String?,
    val updated_at: String,
    val owner: OwnerModel
) : Serializable

data class OwnerModel(
    val id: Int,
    val login: String,
    val avatar_url: String
) : Serializable