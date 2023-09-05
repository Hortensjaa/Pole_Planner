package com.example.poleplanner.poses_list_view.composables.poses_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.poleplanner.data_structure.models.Pose
import com.example.poleplanner.data_structure.references.PoseWithTags
import com.example.poleplanner.navbar.Screen
import com.example.poleplanner.poses_list_view.PoseEvent


@Composable
fun PoseListItem(
    poseWithTags: PoseWithTags = PoseWithTags(Pose("Placeholder"), listOf()),
    posesOnEvent: (PoseEvent) -> Unit,
    navController: NavController
) {
    Column (
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .clickable {
                navController.navigate("${Screen.DetailScreen.route}/${poseWithTags.pose.poseName}")
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NameBar(poseWithTags.pose.poseName, poseWithTags.pose.saved) {
            posesOnEvent(PoseEvent.ChangeSave(poseWithTags.pose))
        }
        Image(
            painter = painterResource(id = poseWithTags.pose.photoResId),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        TagsBar(poseWithTags.tags, posesOnEvent)
    }
}
