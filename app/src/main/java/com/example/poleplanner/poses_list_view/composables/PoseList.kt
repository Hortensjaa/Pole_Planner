package com.example.poleplanner.poses_list_view.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.poleplanner.data_structure.Pose
import com.example.poleplanner.poses_list_view.PosesViewModel

@Composable
fun PoseList(
    viewModel: PosesViewModel,
    navController: NavController,
    poses: List<Pose>
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(bottom = 30.dp)
    ) {
        items(poses) {
            pose ->
            Box(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                PoseListItem(pose, viewModel, navController)
            }
        }
    }
}

