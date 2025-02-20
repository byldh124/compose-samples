package com.moondroid.compose.paging.domain.model

data class User(
    val id: Int,
    val login: String,
    val nodeId: String,
    val avatarUrl: String,
    val reposUrl: String,
)