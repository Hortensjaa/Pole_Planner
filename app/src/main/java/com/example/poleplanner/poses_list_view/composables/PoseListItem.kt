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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.poleplanner.data_structure.Pose
import com.example.poleplanner.data_structure.Tag
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PoseEvent
import com.example.poleplanner.poses_list_view.PoseViewModel
import com.example.poleplanner.ui.theme.AlmostWhite
import com.example.poleplanner.ui.theme.AutoResizedText
import com.example.poleplanner.ui.theme.DarkPink


@Composable
fun PoseListItem(
    pose: Pose = Pose(poseName = "Brass Sit"),
    state: AllPosesState,
    viewModel: PoseViewModel
//    onEvent: (PoseEvent) -> Unit
) {

    var isSaved by remember { mutableStateOf(pose.saved) }
    val heartIcon = if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    Column (
        modifier = Modifier
            .background(color = DarkPink)
            .clickable {/* todo: wejście do podglądu */ },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AutoResizedText(
                text = pose.poseName,
                color = AlmostWhite,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(10.dp))
            Icon(
                imageVector = heartIcon,
                contentDescription = "Save pose",
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        isSaved = !isSaved
                        viewModel.onEvent(PoseEvent.ChangeSave(pose))
                    },
                tint = AlmostWhite,
            )
        }
        Image(
            painter = painterResource(id = pose.photoResId),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Row (modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()))
        {
            val tags = remember { mutableStateOf<List<Tag>>(emptyList()) }
            LaunchedEffect(Unit) {
                val tagsForPose = viewModel.dao.getTagsForPose(pose.poseName)
                tags.value = tagsForPose
            }
            if (tags.value.isNotEmpty()) {
                tags.value.forEach { tag ->
                    Text(
                        text = "#"+tag.tagName.lowercase(),
                        maxLines = 1,
                        color = Color.White,
                        modifier = Modifier
                            .padding(5.dp)
                            .background(color = AlmostWhite.copy(alpha = 0.3f))
                            .padding(vertical = 3.dp, horizontal = 5.dp)
                            .clickable{/*todo: filtrowanie po tagu*/}
                    )
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



