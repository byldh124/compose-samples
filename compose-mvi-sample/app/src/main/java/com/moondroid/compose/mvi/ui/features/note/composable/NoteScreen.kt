package com.moondroid.compose.mvi.ui.features.note.composable

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.moondroid.compose.mvi.R
import com.moondroid.compose.mvi.common.BoxColor
import com.moondroid.compose.mvi.domain.model.Note
import com.moondroid.compose.mvi.ui.features.note.NoteContract
import com.moondroid.compose.mvi.ui.features.note.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel(),
    noteId: String,
) {
    val lifecycleScope = rememberCoroutineScope()
    val mContext = LocalContext.current
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.intent.send(NoteContract.Intent.LoadNote(noteId.toInt()))
        viewModel.effect.collect {
            when (it) {
                NoteContract.Effect.Done -> {
                    Toast.makeText(mContext, "저장완료", Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "메모") },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(
                            onClick = {navController.navigateUp()},
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "뒤로가기"
                            )
                        }
                    }
            })
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (val state = uiState) {
                is NoteContract.State.Error -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = state.message)
                }

                NoteContract.State.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                is NoteContract.State.Success -> NoteWidget(
                    lifecycleScope, viewModel,
                    note = state.note,
                    onValueChange = { s ->
                        lifecycleScope.launch {
                            viewModel.intent.send(NoteContract.Intent.ChangeContent(s))
                        }
                    },
                    onBoxSelect = { boxColor ->
                        lifecycleScope.launch {
                            viewModel.intent.send(NoteContract.Intent.ChangeBoxColor(boxColor))
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun NoteWidget(
    lifecycleScope: CoroutineScope,
    viewModel: NoteViewModel,
    note: Note,
    onValueChange: (String) -> Unit,
    onBoxSelect: (BoxColor) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = Color.Gray)
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = note.description,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = note.boxColor.color,
                focusedContainerColor = note.boxColor.color,
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BoxColor.entries.forEach { boxColor ->
                BoxColorSelector(
                    boxColor = boxColor,
                    isChecked = note.boxColor == boxColor,
                    onClick = onBoxSelect
                )
            }

            Spacer(modifier = Modifier.weight(1.0f))

            Text(
                text = "저장",
                modifier = Modifier
                    .clickable {
                        lifecycleScope.launch {
                            viewModel.intent.send(NoteContract.Intent.Save)
                        }
                    }
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            )
        }
    }
}

@Composable
fun BoxColorSelector(
    boxColor: BoxColor,
    isChecked: Boolean,
    onClick: (BoxColor) -> Unit,
) {
    Card(
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .clickable { onClick(boxColor) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = boxColor.color)
    ) {
        if (isChecked) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    }
}

