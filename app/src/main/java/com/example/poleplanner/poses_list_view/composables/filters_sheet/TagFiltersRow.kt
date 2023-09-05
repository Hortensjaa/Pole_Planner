package com.example.poleplanner.poses_list_view.composables.filters_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PoseEvent
import com.example.poleplanner.ui.theme.TagBox

// todo: dodawanie tagów do filtra z tego poziomu
@Composable
fun TagFiltersRow(
    state: AllPosesState,
    posesOnEvent: (PoseEvent) -> Unit,
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .background(color = MaterialTheme.colorScheme.secondary)
            .horizontalScroll(rememberScrollState())
    )
    {
        if (state.tagFilters.isNotEmpty()) {
            Row {
                state.tagFilters.forEach {
                    tagName ->
                    FilterRowItem(
                        tagName
                    ) { posesOnEvent(PoseEvent.DeleteTagFilter(tagName)) }
                }
            }
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear tags",
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { posesOnEvent(PoseEvent.ClearTagFilter) }
            )
        } else {
            Text(
                text = "wybierz #tag, aby po nim filtrować",
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}


@Preview
@Composable
fun FilterRowItem(
    tagName: String = "statyczne",
    action: () -> Unit = {}
) {
    Row (
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f))
    )
    {
        TagBox(
            tagName = tagName,
            backgroundColor = Color.Transparent
        )
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "Delete tag",
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .align(Alignment.CenterVertically)
                .clickable { action() }
        )
    }
}

