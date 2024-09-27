package com.moondroid.compose.mvi.ui.features.note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moondroid.compose.mvi.common.BoxColor
import com.moondroid.compose.mvi.domain.model.Note
import com.moondroid.compose.mvi.domain.model.response.onError
import com.moondroid.compose.mvi.domain.model.response.onSuccess
import com.moondroid.compose.mvi.domain.usecase.GetNotesUseCase
import com.moondroid.compose.mvi.domain.usecase.SaveUseCase
import com.moondroid.compose.mvi.ui.features.note.NoteContract.*
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
class NoteViewModel @Inject constructor(
    private val saveUseCase: SaveUseCase,
    private val getNotesUseCase: GetNotesUseCase,
) : ViewModel() {

    private val _effect = Channel<Effect>(Channel.UNLIMITED)
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state.asStateFlow()

    val intent = Channel<Intent>(Channel.UNLIMITED)

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect { intent ->
                when (intent) {
                    is Intent.ChangeBoxColor -> onNoteBoxColorChanged(intent.boxColor)
                    is Intent.ChangeContent -> onNoteContentChanged(intent.description)
                    is Intent.LoadNote -> {
                        if (intent.id != 0) getNote(intent.id)
                        else _state.value = State.Success(Note())
                    }

                    Intent.Save -> save()
                }
            }
        }
    }

    private fun onNoteContentChanged(text: String) {
        if (_state.value is State.Success) {
            val state: State.Success = _state.value as State.Success
            val updateNote = state.note.copy(description = text)
            _state.value = State.Success(updateNote)
        }
    }

    private fun onNoteBoxColorChanged(boxColor: BoxColor) {
        if (_state.value is State.Success) {
            val state: State.Success = _state.value as State.Success
            val updateNote = state.note.copy(boxColor = boxColor)
            _state.value = State.Success(updateNote)
        }
    }

    private fun getNote(id: Int) {
        viewModelScope.launch {
            getNotesUseCase(id).collect { result ->
                result.onSuccess {
                    /** emit() vs value
                     *  MutableStateFlow에서 emit()로 value의 차이
                     *  하는 역할은 같으나 emit은 suspend를 포함하여 비동기 처리과정을 돕는다.
                     *  emit()은 CourotineScope 내부에서 호출
                     * @link https://www.baeldung.com/kotlin/mutablestateflow-value-vs-emit
                     * */
                    //_state.value = State.Success(it)
                    _state.emit(State.Success(it))
                }.onError {
                    //_state.value = State.Error(it.message ?: it.javaClass.simpleName)
                    _state.emit(State.Error(it.message ?: it.javaClass.simpleName))
                }
            }
        }
    }

    private fun save() {
        if (_state.value is State.Success) {
            val state: State.Success = _state.value as State.Success
            val updateNote = state.note.copy(date = System.currentTimeMillis())
            viewModelScope.launch {
                saveUseCase(updateNote).collect { result ->
                    Log.e("TAG", "save collect : $result")
                    result.onSuccess {
                        _effect.send(Effect.Done)
                    }.onError {
                        _state.emit(State.Error(it.message ?: it.javaClass.simpleName))
                    }
                }
            }
        }
    }
}