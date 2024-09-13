package com.moondroid.compose.mvi.data.mapper

import com.moondroid.compose.mvi.data.model.entity.NoteEntity
import com.moondroid.compose.mvi.domain.model.Note

object DataMapper {
    fun NoteEntity.toNote(): Note =
        Note(id = id, description = description, date = date, boxColor = boxColor)

    fun Note.toNoteEntity(): NoteEntity =
        NoteEntity(id = id, description = description, date = date, boxColor = boxColor)
}