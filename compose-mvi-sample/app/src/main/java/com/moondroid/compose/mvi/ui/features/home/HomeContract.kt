package com.moondroid.compose.mvi.ui.features.home

import com.moondroid.compose.mvi.domain.model.Note


sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Notes(val data: List<Note>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

sealed interface HomeIntent {
    data object FetchNotes : HomeIntent
    data class Delete(val note: Note) : HomeIntent
}