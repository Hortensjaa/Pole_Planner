package com.example.poleplanner.poses_list_view.composables.poses_list

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.poleplanner.data_structure.models.Tag
import com.example.poleplanner.poses_list_view.PoseEvent
import com.example.poleplanner.ui.theme.TagBox


@Composable
fun TagsBar(
    poseTags: List<Tag>,
    posesOnEvent: (PoseEvent) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .horizontalScroll(rememberScrollState())
    )
    {
        if (poseTags.isNotEmpty()) {
            poseTags.forEach {
                    tag ->
                TagBox(tagName = tag.tagName,
                    action = { posesOnEvent(PoseEvent.AddTagFilter(tag.tagName)) })
            }
        } else {
            Text(
                text = "",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
