package com.example.poleplanner.poses_list_view.composables.poses_list

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
import com.example.poleplanner.data_structure.references.PoseWithTags
import com.example.poleplanner.poses_list_view.PoseEvent

@Composable
fun PoseList(
    posesOnEvent: (PoseEvent) -> Unit,
    navController: NavController,
    posesWithTags: List<PoseWithTags>
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(bottom = 30.dp)
    ) {
        items(posesWithTags) {
            poseWithTags ->
            Box(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                PoseListItem(poseWithTags, posesOnEvent, navController)
            }
        }
    }
}

