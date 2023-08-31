package com.example.poleplanner.poses_list_view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.poleplanner.data_structure.Difficulty
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PoseEvent
import com.example.poleplanner.poses_list_view.PosesViewModel

@Composable
fun DiffFilterBox (
    state: AllPosesState,
    viewModel: PosesViewModel
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp, start = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Poziom")
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Difficulty.values().forEach { diff ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = (diff in state.diffFilters),
                    onCheckedChange = {
                        if (it) viewModel.onEvent(PoseEvent.AddDiffFilter(diff))
                        else viewModel.onEvent(PoseEvent.DeleteDiffFilter(diff))
                    }
                )
                Text(text = diff.toString(), maxLines = 1)
            }
        }
    }
}