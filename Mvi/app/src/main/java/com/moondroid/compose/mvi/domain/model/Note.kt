package com.moondroid.compose.mvi.domain.model

import com.moondroid.compose.mvi.common.BoxColor

data class Note(
    val id: Int = 0,
    var description: String = "",
    val date: Long = 0,
    var boxColor: BoxColor = BoxColor.getRandom(),
)
