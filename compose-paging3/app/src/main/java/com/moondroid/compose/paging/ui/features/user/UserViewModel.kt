package com.moondroid.compose.paging.ui.features.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moondroid.compose.paging.domain.model.User
import com.moondroid.compose.paging.domain.usecase.UserListUseCase
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
class UserViewModel @Inject constructor(
    private val userLIstUseCase: UserListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUIState>(UserUIState.Loading)
    val uiState: StateFlow<UserUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            //Loading 상태를 확인하기 위한 delay
            delay(1000)
            val data = userLIstUseCase().cachedIn(viewModelScope)
            _uiState.emit(UserUIState.List(data))
        }
    }
}