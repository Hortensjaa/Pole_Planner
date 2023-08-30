package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.poleplanner.data_structure.Progress
import com.example.poleplanner.pose_detail_view.DetailEvent
import com.example.poleplanner.pose_detail_view.DetailViewModel

@Composable
fun ProgressRow(
    progress: Progress = Progress.DONE,
    detailVM: DetailViewModel
) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        val color : Color = MaterialTheme.colorScheme.primary
        val iconBorder: ImageVector = Icons.Default.RadioButtonUnchecked
        val iconFilled: ImageVector = Icons.Default.CheckCircle
        val action: (p: Progress) -> Unit
                = {p -> detailVM.onEvent(DetailEvent.SaveProgress(p))}
        when (progress) {
            Progress.NOT_YET -> NotYetRow(color, iconBorder, action)
            Progress.ALMOST -> AlmostRow(color, iconBorder, iconFilled, action)
            Progress.DONE -> DoneRow(color, iconBorder, iconFilled, action)
            Progress.PERFECT -> PerfectRow(color, iconFilled, action)
        }
        Text(
            text = progress.toString(),
            color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun NotYetRow(
    color: Color = MaterialTheme.colorScheme.primary,
    iconBorder: ImageVector = Icons.Default.StarBorder,
    action: (p: Progress) -> Unit = {}
) {
    Row {
        IconButton(onClick = { action(Progress.ALMOST) }) {
            Icon(iconBorder, "1", tint = color)
        }
        IconButton(onClick = { action(Progress.DONE) }) {
            Icon(iconBorder, "2", tint = color)
        }
        IconButton(onClick = { action(Progress.PERFECT) }) {
            Icon(iconBorder, "3", tint = color)
        }
    }
}

@Composable
fun AlmostRow(
    color: Color = MaterialTheme.colorScheme.primary,
    iconBorder: ImageVector = Icons.Default.StarBorder,
    iconFilled: ImageVector = Icons.Default.Star,
    action: (p: Progress) -> Unit = {}
) {
    Row {
        IconButton(onClick = { action(Progress.NOT_YET) }) {
            Icon(iconFilled, "1", tint = color)
        }
        IconButton(onClick = { action(Progress.DONE) }) {
            Icon(iconBorder, "2", tint = color)
        }
        IconButton(onClick = { action(Progress.PERFECT) }) {
            Icon(iconBorder, "3", tint = color)
        }
    }
}

@Composable
fun DoneRow(
    color: Color = MaterialTheme.colorScheme.primary,
    iconBorder: ImageVector = Icons.Default.StarBorder,
    iconFilled: ImageVector = Icons.Default.Star,
    action: (p: Progress) -> Unit = {}
) {
    Row {
        IconButton(onClick = { action(Progress.NOT_YET) }) {
            Icon(iconFilled, "1", tint = color)
        }
        IconButton(onClick = { action(Progress.ALMOST) }) {
            Icon(iconFilled, "2", tint = color)
        }
        IconButton(onClick = { action(Progress.PERFECT) }) {
            Icon(iconBorder, "3", tint = color)
        }
    }
}

@Composable
fun PerfectRow(
    color: Color = MaterialTheme.colorScheme.primary,
    iconFilled: ImageVector = Icons.Default.Star,
    action: (p: Progress) -> Unit = {}
) {
    Row {
        IconButton(onClick = { action(Progress.NOT_YET) }) {
            Icon(iconFilled, "1", tint = color)
        }
        IconButton(onClick = { action(Progress.ALMOST) }) {
            Icon(iconFilled, "2", tint = color)
        }
        IconButton(onClick = { action(Progress.DONE) }) {
            Icon(iconFilled, "3", tint = color)
        }
    }
}