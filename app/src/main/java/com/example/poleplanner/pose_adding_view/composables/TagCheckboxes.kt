package com.example.poleplanner.pose_adding_view.composables

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.test.ext.truth.content.IntentCorrespondences
import com.example.poleplanner.data_structure.models.Tag


@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun TagsCheckboxes(
    allTags: List<Tag> = listOf(Tag("tag 1"), Tag("tag 2"))
) {
    FlowRow (
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ){
        allTags.forEach {
            TagsCheckboxItem(it.tagName)
        }
    }
}


@Preview
@Composable
fun TagsCheckboxItem(
    tagName: String = "tagname"
) {
    Row (
        modifier = Modifier
            .padding(horizontal = 5.dp)
    )
    {
        Text(
            text = "#${tagName.lowercase()}",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(5.dp)
                .clickable { IntentCorrespondences.action() }
                .background(color = MaterialTheme.colorScheme.tertiary)
                .padding(2.dp)
                .background(color = MaterialTheme.colorScheme.background)
                .padding(vertical = 3.dp, horizontal = 5.dp)
        )
    }
}