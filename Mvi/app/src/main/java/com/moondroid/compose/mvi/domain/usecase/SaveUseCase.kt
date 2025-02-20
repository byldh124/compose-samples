package com.moondroid.compose.mvi.domain.usecase

import com.moondroid.compose.mvi.domain.model.Note
import com.moondroid.compose.mvi.domain.repository.NoteRepository
import javax.inject.Inject

class SaveUseCase @Inject constructor(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.save(note)
}