package com.example.poleplanner.poses_list_view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PoseViewModel
import com.example.poleplanner.ui.theme.AlmostWhite

@Composable
fun PoseList(
    state: AllPosesState,
    viewModel: PoseViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(bottom = 30.dp)
    ) {
        items(state.poses) {
            pose ->
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .background(color = AlmostWhite)
            ) {
                PoseListItem(pose, viewModel)
            }
        }
    }
}