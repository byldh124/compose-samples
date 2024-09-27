package com.moondroid.compose.mvi.data.repository

import com.moondroid.compose.mvi.data.datasource.LocalDataSource
import com.moondroid.compose.mvi.data.mapper.DataMapper.toNote
import com.moondroid.compose.mvi.data.mapper.DataMapper.toNoteEntity
import com.moondroid.compose.mvi.domain.model.Note
import com.moondroid.compose.mvi.domain.model.response.Either
import com.moondroid.compose.mvi.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
) : NoteRepository {
    override suspend fun save(note: Note): Flow<Either<Unit>> {
        return if (note.id == 0) {
            insert(note)
        } else {
            update(note)
        }
    }

    private suspend fun insert(note: Note): Flow<Either<Unit>> {
        return flow<Either<Unit>> {
            runCatching {
                emit(Either.Success(localDataSource.insertNote(note.toNoteEntity())))
            }.onFailure {
                emit(Either.Error(it))
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun update(note: Note): Flow<Either<Unit>> {
        return flow<Either<Unit>> {
            runCatching {
                emit(Either.Success(localDataSource.update(note.toNoteEntity())))
            }.onFailure {
                emit(Either.Error(it))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun deleteNote(note: Note): Flow<Either<Unit>> {
        return flow<Either<Unit>> {
            runCatching {
                emit(Either.Success(localDataSource.deleteNote(note.toNoteEntity())))
            }.onFailure {
                emit(Either.Error(it))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getNotes(): Flow<Either<List<Note>>> {
        return flow<Either<List<Note>>> {
            runCatching {
                localDataSource.getNotes().run {
                    emit(Either.Success(this.map { it.toNote() }))
                }
            }.onFailure {
                emit(Either.Error(it))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getNote(id: Int): Flow<Either<Note>> {
        return flow<Either<Note>> {
            runCatching {
                val noteEntity = localDataSource.getNote(id)
                if (noteEntity != null) {
                    emit(Either.Success(noteEntity.toNote()))
                } else {
                    emit(Either.Error(Exception("해당 아이디의 노트가 존재하지 않습니다.")))
                }
            }.onFailure {
                emit(Either.Error(it))
            }
        }.flowOn(Dispatchers.IO)
    }
}
