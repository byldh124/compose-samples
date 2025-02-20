package com.moondroid.compose.mvi.common

import androidx.compose.ui.graphics.Color
import com.moondroid.compose.mvi.ui.theme.*

enum class BoxColor(val color: Color) {
    YELLOW(Memo01),
    PINK(Memo02),
    BLUE(Memo03),
    GREEN(Memo04),
    RED(Memo05);

    companion object {
        fun getRandom(): BoxColor {
            return BoxColor.values().toList().shuffled().first()
        }
    }

}