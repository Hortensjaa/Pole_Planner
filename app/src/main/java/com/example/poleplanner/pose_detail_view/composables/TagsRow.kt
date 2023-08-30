package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.poleplanner.data_structure.Tag
import com.example.poleplanner.ui.theme.TagBox

@Composable
fun TagsRow(
    tags: List<Tag>
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        if (tags.isNotEmpty()) {
            tags.forEach { tag ->
                TagBox(
                    tagName = tag.tagName,
                    backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha=0.9f),
                    textColor = Color.DarkGray
                )
            }
        }
    }
}