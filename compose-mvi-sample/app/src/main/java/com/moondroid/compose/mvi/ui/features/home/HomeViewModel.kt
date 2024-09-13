package com.moondroid.compose.mvi.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moondroid.compose.mvi.domain.model.Note
import com.moondroid.compose.mvi.domain.model.response.onError
import com.moondroid.compose.mvi.domain.model.response.onSuccess
import com.moondroid.compose.mvi.domain.usecase.DeleteUseCase
import com.moondroid.compose.mvi.domain.usecase.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteUseCase: DeleteUseCase,
) : ViewModel(){
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.Delete -> deleteNote(intent.note)
            HomeIntent.FetchNotes -> fetchNotes()
        }
    }

    private fun fetchNotes() {
        viewModelScope.launch {
            getNotesUseCase().collect { result ->
                result.onSuccess {
                    _uiState.emit(HomeUiState.Notes(it))
                }.onError {
                    _uiState.emit(HomeUiState.Error(it.message ?: it.javaClass.simpleName))
                }
            }
        }
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteUseCase(note).collect { result ->
                result.onSuccess {
                    fetchNotes()
                }.onError {
                    //TODO SideEffect
                }
            }
        }
    }
}