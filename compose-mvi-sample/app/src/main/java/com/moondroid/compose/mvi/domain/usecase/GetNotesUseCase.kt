package com.moondroid.compose.mvi.domain.usecase

import com.moondroid.compose.mvi.domain.repository.NoteRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(private val repository: NoteRepository) {
    suspend operator fun invoke() = repository.getNotes()
    suspend operator fun invoke(id: Int) = repository.getNote(id)
}