package com.example.poleplanner.poses_list_view.composables

import androidx.compose.runtime.Composable
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PoseViewModel

// future: wyszukiwarka
@Composable
fun AllScreen(
    state: AllPosesState,
    viewModel: PoseViewModel
) {
    FiltersBar(
        state = state,
        viewModel = viewModel
    )
}
