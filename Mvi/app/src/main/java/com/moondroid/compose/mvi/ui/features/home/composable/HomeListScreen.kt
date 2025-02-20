package com.moondroid.compose.mvi.ui.features.home.composable

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moondroid.compose.mvi.domain.model.Note
import com.moondroid.compose.mvi.ui.features.home.HomeContract
import com.moondroid.compose.mvi.ui.features.home.HomeViewModel
import com.moondroid.compose.mvi.ui.navigation.MyNavigationAction
import kotlinx.coroutines.launch

@Composable
fun HomeListScreen(navigationAction: MyNavigationAction, viewModel: HomeViewModel) {
    val mContext = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleScope = rememberCoroutineScope()

    val requestDeleteNote = remember {
        mutableStateOf<Note?>(null)
    }

    LaunchedEffect(viewModel) {
        viewModel.intent.send(HomeContract.Intent.FetchNotes)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            when (it) {
                is HomeContract.Effect.Toast -> Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (requestDeleteNote.value != null) {
        val note = requestDeleteNote.value!!
        Alert(onDismiss = {
            requestDeleteNote.value = null
        }, onConfirm = {
            lifecycleScope.launch {
                viewModel.intent.send(HomeContract.Intent.Delete(note))
            }
            requestDeleteNote.value = null
        })
    }

    @OptIn(ExperimentalMaterial3Api::class)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "메모 목록") })
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (val state = uiState) {
                is HomeContract.State.Error -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = state.message)
                }

                HomeContract.State.Empty -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "새로운 메모를 추가해보세요.")
                        Box(modifier = Modifier.height(20.dp))
                        Button(onClick = {
                            navigationAction.toNote(0)
                        }) {
                            Text(text = "메모 추가")
                        }
                    }
                }

                HomeContract.State.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                is HomeContract.State.Notes -> Column {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1.0f)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(state.data.size) { idx ->

                            NoteItem(state.data[idx],
                                onClick = {
                                    navigationAction.toNote(it.id)
                                }, onLongClicked = {
                                    requestDeleteNote.value = it
                                })
                        }
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                            .clickable {
                                navigationAction.toNote(0)
                            },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Gray)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Icon(imageVector = Icons.Rounded.Add, tint = Color.White, contentDescription = "+")
                            Spacer(modifier = Modifier.width(width = 10.dp))
                            Text(text = "새로운 메모를 추가해보세요.", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Alert(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "확인")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "취소")
            }
        },
        title = {
            Text(text = "메모 삭제")
        },
        text = {
            Text(text = "정말 삭제하시겠습니까?")
        }
    )
}