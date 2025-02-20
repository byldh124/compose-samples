package com.moondroid.compose.mvi.ui.features.home

import com.moondroid.compose.mvi.domain.model.Note

class HomeContract {
    /**
     * UiState를 구성하는 방법(2가지)
     *  1. sealed class (or sealed interface)를 이용
     *   - 하나의 화면에서 하나의 화면 상태만 표시할때 유용
     *   - 화면 단에서는 when절을 이용해 화면 렌더링이 가능
     *   - 여러 상태가 동시에 렌더링되어 불필요한 Recomposition이 일어나는 것을 막는다.
     *
     *  2. 단일 data class를 이용
     *   - 하나의 화면에서 여러개의 화면 상태를 표시할때 유용
     *   - 화면단에서는 상태에 대해 보장이 되지 않음.
     *   - reducer와 함께 사용
     *
     *  참고)  https://betterprogramming.pub/managing-jetpack-compose-ui-state-with-sealed-classes-d864c1609279
     */
    sealed interface State {
        data object Loading : State
        data class Notes(val data: List<Note>) : State
        data object Empty: State
        data class Error(val message: String) : State
    }

    sealed interface Intent {
        data object FetchNotes : Intent
        data class Delete(val note: Note) : Intent
    }

    sealed interface Effect {
        data class Toast(val message: String): Effect
    }
}
