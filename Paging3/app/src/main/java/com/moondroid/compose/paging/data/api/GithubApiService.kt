package com.moondroid.compose.paging.data.api

import com.moondroid.compose.paging.data.model.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GithubApiService {
    @GET("/users")
    suspend fun getUsers(@Query("since") since: Int, @Query("per_page") num: Int): List<UserDto>

    @GET("/users/{id}")
    suspend fun getUserDetail(@Path("id") userId: String)
}