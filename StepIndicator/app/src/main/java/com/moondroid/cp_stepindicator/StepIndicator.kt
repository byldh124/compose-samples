package com.moondroid.cp_stepindicator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun StepIndicator(state: IndicatorState, stepIndicatorDefault: StepIndicatorDefault = StepIndicatorDefault) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Spacer(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .background(Color.LightGray)
                    .height(1.dp)
                    .cropSize(state.contents.size),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            state.contents.forEachIndexed { index, s ->
                StepIndicatorItem(
                    selected = state.currentPage.value == index,
                    num = index + 1,
                    content = s,
                    stepIndicatorDefault
                )
            }
        }
    }
}

fun Modifier.cropSize(
    itemCount: Int
) = layout { measurable, constraints ->
    // Measure the composable
    val placeable = measurable.measure(constraints)

    val paddingValues = constraints.maxWidth / (itemCount * 2)

    val width = constraints.maxWidth - paddingValues * 2

    layout(width, placeable.height) {
        placeable.placeRelative(placeable.width, placeable.height)
    }
}


@Composable
fun StepIndicatorItem(selected: Boolean, num: Int, content: String, stepIndicatorDefault: StepIndicatorDefault) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val containerColor = if (selected) {
                stepIndicatorDefault.stepIndicatorColors.checkedContainerColor
            } else {
                stepIndicatorDefault.stepIndicatorColors.uncheckedContainerColor
            }

        val labelColor = if (selected) {
                stepIndicatorDefault.stepIndicatorColors.checkedLabelColor
            } else {
                stepIndicatorDefault.stepIndicatorColors.uncheckedLabelColor
            }

        Text(
            num.toString(),
            modifier = Modifier
                .clip(CircleShape)
                .background(containerColor)
                .width(40.dp)
                .aspectRatio(1f)
                .wrapContentHeight(align = Alignment.CenterVertically),
            color = labelColor,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(content)
    }
}

@Composable
fun rememberIndicatorState(initialPage: Int = 0, contents: List<String>): IndicatorState {
    return remember { IndicatorState(mutableIntStateOf(initialPage), contents) }
}

class IndicatorState(
    var currentPage: MutableState<Int> = mutableIntStateOf(0),
    val contents: List<String>,
)

object StepIndicatorDefault {
    val stepIndicatorColors = StepIndicatorColors(
        checkedContainerColor = Color(0xFFFDD835),
        uncheckedContainerColor = Color.Gray,
        checkedLabelColor = Color.White,
        uncheckedLabelColor = Color.White
    )

    @Composable
    fun colors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = contentColorFor(containerColor),
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = contentColor.copy(),
    ): StepIndicatorColors = stepIndicatorColors.copy(
        checkedContainerColor = containerColor,
        uncheckedContainerColor = contentColor,
        checkedLabelColor = disabledContainerColor,
        uncheckedLabelColor = disabledContentColor
    )
}

@Immutable
class StepIndicatorColors constructor(
    val checkedContainerColor: Color,
    val uncheckedContainerColor: Color,
    val checkedLabelColor: Color,
    val uncheckedLabelColor: Color,
) {
    /**
     * Returns a copy of this CardColors, optionally overriding some of the values.
     * This uses the Color.Unspecified to mean “use the value from the source”
     */
    fun copy(
        checkedContainerColor: Color = this.checkedContainerColor,
        uncheckedContainerColor: Color = this.uncheckedContainerColor,
        checkedLabelColor: Color = this.checkedLabelColor,
        uncheckedLabelColor: Color = this.uncheckedLabelColor
    ) = StepIndicatorColors(
        checkedContainerColor.takeOrElse { this.checkedContainerColor },
        uncheckedContainerColor.takeOrElse { this.uncheckedContainerColor },
        checkedLabelColor.takeOrElse { this.checkedLabelColor },
        uncheckedLabelColor.takeOrElse { this.uncheckedLabelColor },
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is StepIndicatorColors) return false

        if (checkedContainerColor != other.checkedContainerColor) return false
        if (uncheckedContainerColor != other.uncheckedContainerColor) return false
        if (checkedLabelColor != other.checkedLabelColor) return false
        if (uncheckedLabelColor != other.uncheckedLabelColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = checkedContainerColor.hashCode()
        result = 31 * result + uncheckedContainerColor.hashCode()
        result = 31 * result + checkedLabelColor.hashCode()
        result = 31 * result + uncheckedLabelColor.hashCode()
        return result
    }
}