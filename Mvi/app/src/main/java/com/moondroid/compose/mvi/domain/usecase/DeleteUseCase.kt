package com.moondroid.compose.mvi.domain.usecase

import com.moondroid.compose.mvi.domain.model.Note
import com.moondroid.compose.mvi.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteUseCase @Inject constructor(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.deleteNote(note)
}