package com.example.poleplanner.poses_list_view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PosesViewModel

// future: wyszukiwarka
@Composable
fun AllScreen(
    state: AllPosesState,
    viewModel: PosesViewModel,
    navController: NavController,
) {
    FiltersBar(
        state = state,
        viewModel = viewModel,
        content =
        {
            Column (
                modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
            )
            {
                TagFiltersRow(state, viewModel)
                PoseList(state, viewModel, navController)
            }
        }
    )
}
