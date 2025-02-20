package com.moondroid.compose.mvi.data.datasource

import com.moondroid.compose.mvi.data.model.dao.NoteDao
import com.moondroid.compose.mvi.data.model.entity.NoteEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val noteDao: NoteDao,
) : LocalDataSource {
    override suspend fun insertNote(note: NoteEntity) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        noteDao.delete(note)
    }

    override suspend fun getNotes(): List<NoteEntity> {
        return noteDao.getNoteAll()
    }

    override suspend fun getNote(id: Int): NoteEntity? {
        return noteDao.getNote(id)
    }

    override suspend fun update(note: NoteEntity) {
        noteDao.update(note.id, note.description, note.boxColor, note.date)
    }
}