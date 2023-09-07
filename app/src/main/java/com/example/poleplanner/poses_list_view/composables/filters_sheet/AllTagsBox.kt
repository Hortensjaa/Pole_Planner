package com.example.poleplanner.poses_list_view.composables.filters_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PoseEvent


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AllTagsBox(
    state: AllPosesState,
    posesOnEvent: (PoseEvent) -> Unit,
) {
    FlowRow (
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ){
        state.allTags.forEach {
            AllTagsItem(it.tagName, state) {
                posesOnEvent(PoseEvent.ChangeTagFilter(it.tagName))
            }
        }
    }
}


@Composable
fun AllTagsItem(
    tagName: String = "statyczne",
    state: AllPosesState,
    action: () -> Unit = {}
) {
    Row (
        modifier = Modifier
            .padding(horizontal = 5.dp)
    )
    {
        val frame = if (tagName in state.tagFilters) MaterialTheme.colorScheme.background
                    else Color.LightGray
        val filling = if (tagName in state.tagFilters) MaterialTheme.colorScheme.primary
                      else Color.White
        val textColor = if (tagName in state.tagFilters) MaterialTheme.colorScheme.background
                        else Color.DarkGray
        Text(
            text = "#${tagName.lowercase()}",
            color = textColor,
            modifier = Modifier
                .padding(5.dp)
                .clickable { action() }
                .background(color = frame)
                .padding(2.dp)
                .background(color = filling)
                .padding(vertical = 3.dp, horizontal = 5.dp)
        )
    }
}