package com.moondroid.compose.paging.data.di

import com.moondroid.compose.paging.data.repository.RepositoryImpl
import com.moondroid.compose.paging.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideAppRepository(repository: RepositoryImpl): Repository
}