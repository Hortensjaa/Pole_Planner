package com.example.poleplanner.poses_list_view.composables.poses_list

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.poleplanner.data_structure.Pose
import com.example.poleplanner.navbar.Screen
import com.example.poleplanner.poses_list_view.PoseEvent
import com.example.poleplanner.poses_list_view.PosesViewModel
import com.example.poleplanner.ui.theme.AutoResizedText
import com.example.poleplanner.ui.theme.TagBox


@Composable
fun PoseListItem(
    pose: Pose = Pose(poseName = "Brass Sit"),
    viewModel: PosesViewModel,
    navController: NavController
) {
    Column (
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .clickable {
                navController.navigate("${Screen.DetailScreen.route}/${pose.poseName}")
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NameBar(pose, viewModel)
        Image(
            painter = painterResource(id = pose.photoResId),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        TagsBar(pose, viewModel)
    }
}

@Composable
fun NameBar(
    pose: Pose,
    poseVM: PosesViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val poseSave by poseVM.poseDao.getSaveByName(pose.poseName).collectAsState(false)
        AutoResizedText(
            text = pose.poseName,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(10.dp)
        )
        Icon(
            imageVector = if (poseSave) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Save pose",
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    poseVM.onEvent(PoseEvent.ChangeSave(pose))
                },
            tint = MaterialTheme.colorScheme.background,
        )
    }
}

@Composable
fun TagsBar(
    pose: Pose,
    poseVM: PosesViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .horizontalScroll(rememberScrollState())
    )
    {
        val poseTags by poseVM.PTdao.getTagsForPose(pose.poseName)
            .collectAsState(emptyList())
        if (poseTags.isNotEmpty()) {
            poseTags.forEach {
                    tag ->
                TagBox(tagName = tag.tagName,
                    action = { poseVM.onEvent(PoseEvent.AddTagFilter(tag.tagName)) })
            }
        } else {
            Text(
                text = "",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}



