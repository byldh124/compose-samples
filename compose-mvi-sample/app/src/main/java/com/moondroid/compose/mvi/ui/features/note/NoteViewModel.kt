package com.moondroid.compose.mvi.ui.features.note

import androidx.lifecycle.ViewModel
import com.moondroid.compose.mvi.domain.usecase.DeleteUseCase
import com.moondroid.compose.mvi.domain.usecase.GetNotesUseCase
import com.moondroid.compose.mvi.domain.usecase.InsertNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
) : ViewModel() {

}