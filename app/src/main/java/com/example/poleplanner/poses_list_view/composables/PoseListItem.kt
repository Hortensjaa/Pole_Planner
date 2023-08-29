package com.example.poleplanner.poses_list_view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.poleplanner.data_structure.Pose
import com.example.poleplanner.poses_list_view.PoseEvent
import com.example.poleplanner.poses_list_view.PosesViewModel
import com.example.poleplanner.ui.theme.AutoResizedText
import com.example.poleplanner.ui.theme.TagBox


@Composable
fun PoseListItem(
    pose: Pose = Pose(poseName = "Brass Sit"),
    viewModel: PosesViewModel
) {

    var isSaved by remember { mutableStateOf(pose.saved) }
    val heartIcon = if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    Column (
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .clickable {/* todo: wejście do podglądu */ },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AutoResizedText(
                text = pose.poseName,
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(10.dp)
            )
            Icon(
                imageVector = heartIcon,
                contentDescription = "Save pose",
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        isSaved = !isSaved
                        viewModel.onEvent(PoseEvent.ChangeSave(pose))
                    },
                tint = MaterialTheme.colorScheme.background,
            )
        }
        Image(
            painter = painterResource(id = pose.photoResId),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .horizontalScroll(rememberScrollState())
        )
        {
            val poseTags by viewModel.PTdao.getTagsForPose(pose.poseName)
                .collectAsState(emptyList())
            if (poseTags.isNotEmpty()) {
                poseTags.forEach {
                    tag ->
                    TagBox(tagName = tag.tagName,
                    action = { viewModel.onEvent(PoseEvent.AddTagFilter(tag.tagName)) })
                }
            } else {
                Text(
                    text = "",
                    modifier = Modifier.padding(8.dp)
                )
            }

        }
    }
}



