package com.moondroid.compose.paging.ui.features.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moondroid.compose.paging.domain.model.User
import com.moondroid.compose.paging.domain.usecase.UserLIstUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface UserUIState {
    data object Loading : UserUIState
    data class List(val data: Flow<PagingData<User>>) : UserUIState
}

@HiltViewModel
class UserViewModel @Inject constructor(private val userLIstUseCase: UserLIstUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUIState>(UserUIState.Loading)
    val uiState: StateFlow<UserUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            val data = userLIstUseCase().cachedIn(viewModelScope)
            _uiState.emit(UserUIState.List(data))
        }
    }
}