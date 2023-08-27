package com.example.poleplanner.all_poses_view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.poleplanner.all_poses_view.AllPosesState
import com.example.poleplanner.all_poses_view.PoseEvent
import com.example.poleplanner.data_structure.Difficulty


@Composable
fun FiltersBarContent(
    state: AllPosesState,
    onEvent: (PoseEvent) -> Unit
    ) {
        Column(
        modifier = Modifier.background(color = Color.White).padding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp, start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Poziom")
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    onEvent(PoseEvent.ClearDiffFilter)
                }
            ) {
                Text(text = "wyczyść")
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Difficulty.values().forEach { diff ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = state.diffFilter == diff,
                        onClick = {
                            onEvent(PoseEvent.FilterByDiff(diff))
                        }
                    )
                    Text(text = diff.name, maxLines = 1)
                }
            }
        }
    }
}