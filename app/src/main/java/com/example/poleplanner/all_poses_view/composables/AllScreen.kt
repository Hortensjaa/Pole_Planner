package com.example.poleplanner.all_poses_view.composables

import androidx.compose.runtime.Composable
import com.example.poleplanner.all_poses_view.AllPosesState
import com.example.poleplanner.all_poses_view.PoseEvent

@Composable
fun AllScreen(
    state: AllPosesState,
    onEvent: (PoseEvent) -> Unit
) {
    FiltersBar(
        state = state,
        onEvent = onEvent
    )
}
