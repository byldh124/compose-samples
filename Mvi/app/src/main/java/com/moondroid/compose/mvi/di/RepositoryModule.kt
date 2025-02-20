package com.moondroid.compose.mvi.di

import com.moondroid.compose.mvi.data.datasource.LocalDataSource
import com.moondroid.compose.mvi.data.datasource.LocalDataSourceImpl
import com.moondroid.compose.mvi.data.repository.NoteRepositoryImpl
import com.moondroid.compose.mvi.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideNoteRepository(repository: NoteRepositoryImpl): NoteRepository

}

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalBindModule {
    @Binds
    @Singleton
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource
}