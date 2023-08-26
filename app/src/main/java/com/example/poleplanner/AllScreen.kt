package com.example.poleplanner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.poleplanner.data_structure.AppState
import com.example.poleplanner.data_structure.PoseEvent
import com.example.poleplanner.posecomposables.PoseListItem
import com.example.poleplanner.ui.theme.AlmostWhite

@Composable
fun AllScreen(
    state: AppState,
    onEvent: (PoseEvent) -> Unit
) {
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
