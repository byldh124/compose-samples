package com.moondroid.compose.mvi.domain.repository

import com.moondroid.compose.mvi.domain.model.Note
import com.moondroid.compose.mvi.domain.model.response.Either
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun insertNote(note: Note): Flow<Either<Unit>>
    suspend fun deleteNote(note: Note): Flow<Either<Unit>>
    suspend fun getNotes(): Flow<Either<List<Note>>>
    suspend fun getNote(id: Int): Flow<Either<Note>>
    suspend fun update(note: Note): Flow<Either<Unit>>
}