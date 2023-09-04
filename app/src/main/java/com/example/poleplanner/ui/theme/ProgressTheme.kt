package com.example.poleplanner.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.poleplanner.data_structure.models.Progress

@Composable
fun ProgressEmpty (
    color: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    Icon(
        Icons.Default.RadioButtonUnchecked,
        "",
        tint = color,
        modifier = modifier
    )
}

@Composable
fun ProgressFilled (
    color: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    Icon(
        Icons.Default.Circle,
        "",
        tint = color,
        modifier = modifier
    )
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun ProgressRow (
    progress: Progress,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Row {
        for (i in 0..<progress.ordinal) {
            ProgressFilled(color)
        }
        for (i in progress.ordinal..<3) {
            ProgressEmpty(color)
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun ProgressActionRow (
    progress: Progress,
    color: Color = MaterialTheme.colorScheme.primary,
    action: (p: Progress) -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val logicList = Progress.values().toMutableList()
    logicList.removeIf(progress::equals)
    Row {
        for (i in 0..<progress.ordinal) {
            ProgressFilled(
                color = color,
                modifier = Modifier
                    .padding(7.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null){action(logicList[i])})
        }
        for (i in progress.ordinal..<3) {
            ProgressEmpty(
                color = color,
                modifier = Modifier
                    .padding(7.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null){action(logicList[i])})
        }
    }
}
