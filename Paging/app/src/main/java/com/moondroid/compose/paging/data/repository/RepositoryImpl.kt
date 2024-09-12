package com.moondroid.compose.paging.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moondroid.compose.paging.data.datasource.RemoteDataSource
import com.moondroid.compose.paging.domain.model.User
import com.moondroid.compose.paging.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) : Repository {
    override suspend fun getUsers(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { PagingSource(remoteDataSource) }
        ).flow
    }
}

class PagingSource(
    private val remoteDataSource: RemoteDataSource,
) : PagingSource<Int, User>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val since = params.key ?: 0
        return try {
            val response = remoteDataSource.getUsers(since)
            val items = response.map { it.toDomainModel() }
            LoadResult.Page(
                data = items,
                prevKey = null,
                nextKey = if (items.isEmpty()) null else items.last().id
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.id
        }
    }
}
