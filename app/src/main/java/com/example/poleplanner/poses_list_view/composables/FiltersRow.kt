package com.example.poleplanner.poses_list_view.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PoseEvent
import com.example.poleplanner.poses_list_view.PoseViewModel
import com.example.poleplanner.ui.theme.DarkPink
import com.example.poleplanner.ui.theme.TagBox

@Composable
fun FiltersRow(
    state: AllPosesState,
    viewModel: PoseViewModel
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    )
    {
        Row {
            if (state.tagFilters.isNotEmpty()) {
                state.tagFilters.forEach {
                        tagName ->
                    TagBox(
                        tagName = tagName,
                        backgroundColor = DarkPink.copy(alpha = 0.7f)
                    )
                }
            } else {
                Text(
                    text = "wybierz tag, aby po nim filtrować",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp)
                )
            }
    }
    Icon(
        imageVector = Icons.Default.Clear,
        contentDescription = "Clear tags",
        modifier = Modifier
            .padding(15.dp)
            .size(20.dp)
            .clickable { viewModel.onEvent(PoseEvent.ClearTagFilter) },
        )
    }
}