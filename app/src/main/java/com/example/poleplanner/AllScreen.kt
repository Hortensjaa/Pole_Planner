package com.example.poleplanner

import android.provider.MediaStore.Audio.Radio
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.poleplanner.data_structure.AllPosesState
import com.example.poleplanner.data_structure.PoseEvent
import com.example.poleplanner.data_structure.SortType
import com.example.poleplanner.posecomposables.PoseListItem
import com.example.poleplanner.ui.theme.AlmostWhite

@Composable
fun AllScreen(
    state: AllPosesState,
    onEvent: (PoseEvent) -> Unit
) {
    Column {
        // filtry i sortowanie
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            RadioButton(
                selected = state.sortType == SortType.NAME,
                onClick = {
                    onEvent(PoseEvent.SortPoses(SortType.NAME))
                }
            )
            RadioButton(
                selected = state.sortType == SortType.ID,
                onClick = {
                    onEvent(PoseEvent.SortPoses(SortType.ID))
                }
            )
        }

        // lista figur
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)) {
            items(state.poses) {
                    pose ->
                Box (
                    modifier = Modifier
                        .padding(5.dp)
                        .background(color = AlmostWhite)
                ) {
                    PoseListItem(pose)
                }
            }
        }
}

}
