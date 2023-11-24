package com.example.poleplanner.poses_list_view.composables.filters_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PoseEvent


@Composable
fun FiltersSheetContent(
    state: AllPosesState,
    posesOnEvent: (PoseEvent) -> Unit,
) {
    Column(
    modifier = Modifier
        .background(color = MaterialTheme.colorScheme.secondary)
        .padding()
    ) {
        AllTagsBox(state) {
            t -> posesOnEvent(PoseEvent.ChangeTagFilter(t))
        }
        DiffFilterBox(
            state = state,
            addingAction = { d -> posesOnEvent(PoseEvent.AddDiffFilter(d)) },
            deleteAction = { d -> posesOnEvent(PoseEvent.DeleteDiffFilter(d)) },
        )
        ProgressFilterBox(
            state = state,
            addingAction = { p -> posesOnEvent(PoseEvent.AddProgressFilter(p)) },
            deleteAction = { p -> posesOnEvent(PoseEvent.DeleteProgressFilter(p)) }
        )
        AddedFilterBox(state.addedByUserOnly) {
            posesOnEvent(PoseEvent.ChangeAddedFilter)
        }
    }
}
