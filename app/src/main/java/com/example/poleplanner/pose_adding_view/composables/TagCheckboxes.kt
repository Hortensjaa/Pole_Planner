package com.example.poleplanner.pose_adding_view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.poleplanner.pose_adding_view.PoseAddingEvent
import com.example.poleplanner.pose_adding_view.PoseAddingState
import com.example.poleplanner.ui.theme.TagsListItem


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsCheckboxes(
    state: PoseAddingState,
    onEvent: (PoseAddingEvent) -> Unit
) {
    FlowRow (
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ){
        state.allTags.forEach {
            TagsListItem(it.tagName, (it in state.selectedTags)) {
                onEvent(PoseAddingEvent.UpdateTags(it))
            }
        }
    }
}
