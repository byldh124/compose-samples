package com.moondroid.compose.mvi.data.repository

import com.moondroid.compose.mvi.data.datasource.LocalDataSource
import com.moondroid.compose.mvi.data.mapper.DataMapper.toNote
import com.moondroid.compose.mvi.data.mapper.DataMapper.toNoteEntity
import com.moondroid.compose.mvi.domain.model.Note
import com.moondroid.compose.mvi.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
) : NoteRepository {
    override suspend fun insertNote(note: Note): Flow<Int> {
        return flow {
            emit(localDataSource.insertNote(note.toNoteEntity()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun deleteNote(note: Note): Flow<Int> {
        return flow {
            emit(localDataSource.deleteNote(note.toNoteEntity()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getNotes(): Flow<List<Note>> {
        return flow {
            localDataSource.getNotes().run {
                emit(this.map { it.toNote() })
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getNote(id: Int): Flow<Note?> {
        return flow {
            localDataSource.getNote(id).run {
                emit(this?.toNote())
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun update(note: Note): Flow<Int> {
        return flow {
            emit(localDataSource.update(note.toNoteEntity()))
        }.flowOn(Dispatchers.IO)
    }
}
