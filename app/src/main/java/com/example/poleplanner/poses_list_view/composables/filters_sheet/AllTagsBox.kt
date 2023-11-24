package com.example.poleplanner.poses_list_view.composables.filters_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.ui.theme.TagsListItem


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AllTagsBox(
    state: AllPosesState,
    action: (String) -> Unit
) {
    FlowRow (
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ){
        state.allTags.forEach {
            TagsListItem(it.tagName, (it.tagName in state.tagFilters)) {
                action(it.tagName)
            }
        }
    }
}
