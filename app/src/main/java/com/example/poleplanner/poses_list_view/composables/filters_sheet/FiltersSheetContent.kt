package com.example.poleplanner.poses_list_view.composables.filters_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PosesViewModel


@Composable
fun FiltersSheetContent(
    state: AllPosesState,
    viewModel: PosesViewModel
) {
    Column(
    modifier = Modifier
        .background(color = MaterialTheme.colorScheme.secondary)
        .padding()
    ) {
        TagFiltersRow(state, viewModel)
        DiffFilterBox(state, viewModel)
    }
}
