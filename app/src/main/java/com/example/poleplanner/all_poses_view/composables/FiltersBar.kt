package com.example.poleplanner.all_poses_view.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.poleplanner.all_poses_view.AllPosesState
import com.example.poleplanner.all_poses_view.PoseEvent
import com.example.poleplanner.data_structure.Difficulty

@Composable
fun FiltersBar(
    state: AllPosesState,
    onEvent: (PoseEvent) -> Unit
) {
    Column {

        // filtrowanie
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Poziom")
                Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the right
                Button(
                    onClick = {
                        onEvent(PoseEvent.ClearDiffFilter)
                    }
                ) {
                    Text(text = "wyczyść")
                }
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
                ) {
                Difficulty.values().forEach {
                    diff ->
                    RadioButton(
                        selected = state.diffFilter == diff,
                        onClick = {
//                           if (state.progFilter == null) {
                                onEvent(PoseEvent.FilterByDiff(diff))
//                           }
                        }
                    )
                    Text(text = diff.name)
                }
            }
        }
    }
}
