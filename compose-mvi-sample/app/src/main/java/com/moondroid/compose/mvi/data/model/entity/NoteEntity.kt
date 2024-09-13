package com.moondroid.compose.mvi.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moondroid.compose.mvi.common.BoxColor

@Entity(tableName = "MyNote")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("date")
    val date: Long,
    @ColumnInfo("boxColor")
    val boxColor: BoxColor,
)