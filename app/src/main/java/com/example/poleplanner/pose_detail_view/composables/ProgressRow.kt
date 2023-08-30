package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.poleplanner.data_structure.Progress

@Preview
@Composable
fun ProgressRow(
    progress: Progress = Progress.DONE
) {
    Column {
        when (progress) {
            Progress.NOT_YET -> NotYetRow()
            Progress.ALMOST -> AlmostRow()
            Progress.DONE -> DoneRow()
            Progress.PERFECT -> PerfectRow()
        }
        Text(text = progress.toString())
    }
}

@Composable
fun NotYetRow() {
    Row {
        Icon(Icons.Default.StarBorder, "1")
        Icon(Icons.Default.StarBorder, "2")
        Icon(Icons.Default.StarBorder, "3")
    }
}

@Composable
fun AlmostRow() {
    Row {
        Icon(Icons.Default.Star, "1")
        Icon(Icons.Default.StarBorder, "2")
        Icon(Icons.Default.StarBorder, "3")
    }
}

@Composable
fun DoneRow() {
    Row {
        Icon(Icons.Default.Star, "1")
        Icon(Icons.Default.Star, "2")
        Icon(Icons.Default.StarBorder, "3")
    }
}

@Composable
fun PerfectRow() {
    Row {
        Icon(Icons.Default.Star, "1")
        Icon(Icons.Default.Star, "2")
        Icon(Icons.Default.Star, "3")
    }
}