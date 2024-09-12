package com.moondroid.compose.paging.data.api

import retrofit2.http.GET

interface MovieApiService {
    @GET
    suspend fun getMovies()
}