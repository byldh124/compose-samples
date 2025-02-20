package com.moondroid.compose.mvi.ui.features.home.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moondroid.compose.mvi.domain.model.Note
import com.moondroid.compose.mvi.ui.theme.CAFE_24
import com.moondroid.compose.mvi.ui.theme.NANUM_EB
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(note: Note, onClick: (Note) -> Unit, onLongClicked: (Note) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .combinedClickable(
                onClick = {
                    onClick(note)
                },
                onLongClick = {
                    onLongClicked(note)
                }
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = note.boxColor.color
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                Text(
                    text = getDateFormat(note.date),
                    fontFamily = CAFE_24
                )
            }
            Box {
                Text(
                    text = note.description,
                    fontFamily = NANUM_EB
                )
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
private fun getDateFormat(date: Long): String {
    return SimpleDateFormat("yyyy MM dd HH:mm:ss").format(Date(date))
}
