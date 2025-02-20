package com.moondroid.compose.paging.domain.repository

import androidx.paging.PagingData
import com.moondroid.compose.paging.domain.model.User
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getUsers(): Flow<PagingData<User>>
}