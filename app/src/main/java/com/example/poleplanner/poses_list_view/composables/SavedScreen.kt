package com.example.poleplanner.poses_list_view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PosesViewModel

// future: tworzenie kolekcji
@Composable
fun SavedScreen(
        state: AllPosesState,
        viewModel: PosesViewModel,
        navController: NavController,
) {
        FiltersSheet(
                state = state,
                viewModel = viewModel,
                content =
                {
                        Column (
                                modifier = Modifier
                                        .fillMaxWidth()
                                        .background(color = MaterialTheme.colorScheme.background)
                                        .padding(horizontal = 10.dp)
                        )
                        {
                                SearchBar(viewModel, state.searchText)
                                PoseList(viewModel, navController, state.poses)
                        }
                }
        )
}