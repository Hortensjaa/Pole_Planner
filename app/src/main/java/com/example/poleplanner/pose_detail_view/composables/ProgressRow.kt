package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.poleplanner.data_structure.Progress

@Preview
@Composable
fun ProgressRow(
    progress: Progress = Progress.DONE
) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        when (progress) {
            Progress.NOT_YET -> NotYetRow()
            Progress.ALMOST -> AlmostRow()
            Progress.DONE -> DoneRow()
            Progress.PERFECT -> PerfectRow()
        }
        Text(
            text = progress.toString(),
            color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun NotYetRow(
    color: Color = MaterialTheme.colorScheme.primary
) {
    Row {
        Icon(Icons.Default.StarBorder, "1", tint = color)
        Icon(Icons.Default.StarBorder, "2", tint = color)
        Icon(Icons.Default.StarBorder, "3", tint = color)
    }
}

@Composable
fun AlmostRow(
    color: Color = MaterialTheme.colorScheme.primary
) {
    Row {
        Icon(Icons.Default.Star, "1", tint = color)
        Icon(Icons.Default.StarBorder, "2", tint = color)
        Icon(Icons.Default.StarBorder, "3", tint = color)
    }
}

@Composable
fun DoneRow(
    color: Color = MaterialTheme.colorScheme.primary
) {
    Row {
        Icon(Icons.Default.Star, "1", tint = color)
        Icon(Icons.Default.Star, "2", tint = color)
        Icon(Icons.Default.StarBorder, "3", tint = color)
    }
}

@Composable
fun PerfectRow(
    color: Color = MaterialTheme.colorScheme.primary
) {
    Row {
        Icon(Icons.Default.Star, "1", tint = color)
        Icon(Icons.Default.Star, "2", tint = color)
        Icon(Icons.Default.Star, "3", tint = color)
    }
}