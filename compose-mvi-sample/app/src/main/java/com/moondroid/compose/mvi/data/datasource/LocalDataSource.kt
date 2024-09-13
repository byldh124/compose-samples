package com.moondroid.compose.mvi.data.datasource

import com.moondroid.compose.mvi.data.model.entity.NoteEntity

interface LocalDataSource {
    suspend fun insertNote(note: NoteEntity): Int
    suspend fun deleteNote(note: NoteEntity): Int
    suspend fun getNotes(): List<NoteEntity>
    suspend fun getNote(id: Int): NoteEntity?
    suspend fun update(note: NoteEntity): Int
}