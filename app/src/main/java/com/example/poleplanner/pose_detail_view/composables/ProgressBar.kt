package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.poleplanner.data_structure.models.Progress
import com.example.poleplanner.pose_detail_view.DetailEvent
import com.example.poleplanner.pose_detail_view.DetailViewModel
import com.example.poleplanner.ui.theme.ProgressActionRow

@Composable
fun ProgressBar(
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
        ProgressActionRow(
            progress = progress,
            action = { p -> detailVM.onEvent(DetailEvent.SaveProgress(p)) })
        Text(
            text = progress.toString(),
            color = MaterialTheme.colorScheme.primary)
    }
}
