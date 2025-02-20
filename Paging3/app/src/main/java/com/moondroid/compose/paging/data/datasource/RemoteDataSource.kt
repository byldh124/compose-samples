package com.moondroid.compose.paging.data.datasource

import com.moondroid.compose.paging.data.model.dto.UserDto

interface RemoteDataSource {
    suspend fun getUsers(since: Int): List<UserDto>
    suspend fun getUser(userId: String)

    suspend fun getMovies()
}