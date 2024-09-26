package com.moondroid.compose.mvi.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moondroid.compose.mvi.domain.model.Note
import com.moondroid.compose.mvi.domain.model.response.onError
import com.moondroid.compose.mvi.domain.model.response.onSuccess
import com.moondroid.compose.mvi.domain.usecase.DeleteUseCase
import com.moondroid.compose.mvi.domain.usecase.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteUseCase: DeleteUseCase,
) : ViewModel() {
    val intent = Channel<HomeContract.Intent>(Channel.UNLIMITED)
    private val _effect = Channel<HomeContract.Effect>()
    val effect: Flow<HomeContract.Effect> = _effect.consumeAsFlow()

    private val _uiState = MutableStateFlow<HomeContract.State>(HomeContract.State.Loading)
    val uiState: StateFlow<HomeContract.State> = _uiState.asStateFlow()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when (it) {
                    HomeContract.Intent.FetchNotes -> fetchNotes()
                    is HomeContract.Intent.Delete -> deleteNote(it.note)
                }
            }
        }
    }

    private fun fetchNotes() {
        viewModelScope.launch {
            getNotesUseCase().collect { result ->
                result.onSuccess {
                    if (it.isNotEmpty()) {
                        _uiState.emit(HomeContract.State.Notes(it))
                    } else {
                        _uiState.emit(HomeContract.State.Empty)
                    }
                }.onError {
                    _effect.send(HomeContract.Effect.Toast(it.message ?: it.javaClass.simpleName))
                    _uiState.emit(HomeContract.State.Error(it.message ?: it.javaClass.simpleName))
                }
            }
        }
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteUseCase(note).collect { result ->
                result.onSuccess {
                    _effect.send(HomeContract.Effect.Toast("삭제 실패"))
                    fetchNotes()
                }.onError {
                    _effect.send(HomeContract.Effect.Toast("삭제 실패"))
                }
            }
        }
    }
}