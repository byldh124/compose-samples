package com.moondroid.compose.mvi.domain.repository

import com.moondroid.compose.mvi.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun insertNote(note: Note): Flow<Int>
    suspend fun deleteNote(note: Note): Flow<Int>
    suspend fun getNotes(): Flow<List<Note>>
    suspend fun getNote(id: Int): Flow<Note?>
    suspend fun update(note: Note): Flow<Int>
}