package com.example.poleplanner.poses_list_view.composables.filters_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.poleplanner.data_structure.models.Progress
import com.example.poleplanner.poses_list_view.AllPosesState

@Composable
fun ProgressFilterBox (
    state: AllPosesState,
    addingAction: (Progress) -> Unit,
    deleteAction: (Progress) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp, start = 20.dp, top = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider()
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Progress.values().forEach { progress ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = (progress in state.progressFilters),
                    onCheckedChange = {
                        if (it) addingAction(progress)
                        else deleteAction(progress)
                    }
                )
                Text(text = progress.toString(), maxLines = 1)
            }
        }
    }
}