package com.moondroid.compose.mvi.data.datasource

import com.moondroid.compose.mvi.data.model.dao.NoteDao
import com.moondroid.compose.mvi.data.model.entity.NoteEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val noteDao: NoteDao,
) : LocalDataSource {
    override suspend fun insertNote(note: NoteEntity): Int {
        return noteDao.insertNote(note).toInt()
    }

    override suspend fun deleteNote(note: NoteEntity): Int {
        return noteDao.delete(note)
    }

    override suspend fun getNotes(): List<NoteEntity> {
        return noteDao.getNoteAll()
    }

    override suspend fun getNote(id: Int): NoteEntity? {
        return noteDao.getNote(id)
    }

    override suspend fun update(note: NoteEntity): Int {
        return noteDao.update(note.id, note.description, note.boxColor)
    }
}