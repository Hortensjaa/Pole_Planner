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
import com.example.poleplanner.data_structure.models.Difficulty
import com.example.poleplanner.poses_list_view.AllPosesState

@Composable
fun DiffFilterBox (
    state: AllPosesState,
    addingAction: (Difficulty) -> Unit,
    deleteAction: (Difficulty) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider()
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
                        if (it) addingAction(diff)
                        else deleteAction(diff)
                    }
                )
                Text(text = diff.toString(), maxLines = 1)
            }
        }
    }
}