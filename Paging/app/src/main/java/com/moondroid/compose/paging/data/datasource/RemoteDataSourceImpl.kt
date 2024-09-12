package com.moondroid.compose.paging.data.datasource

import com.moondroid.compose.paging.data.api.GithubApiService
import com.moondroid.compose.paging.data.api.MovieApiService
import com.moondroid.compose.paging.data.model.dto.UserDto
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val githubApiService: GithubApiService,
    private val movieApiService: MovieApiService,
) : RemoteDataSource {
    override suspend fun getUsers(since: Int): List<UserDto> {
        return githubApiService.getUsers(since, 10)
    }

    override suspend fun getUser(userId: String) {
        //TODO("Not yet implemented")
    }

    override suspend fun getMovies() {
        TODO("Not yet implemented")
    }
}