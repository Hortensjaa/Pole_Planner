package com.example.poleplanner.all_poses_view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poleplanner.all_poses_view.PoseEvent
import com.example.poleplanner.data_structure.Pose
import com.example.poleplanner.ui.theme.AlmostWhite
import com.example.poleplanner.ui.theme.DarkPink

@Preview
@Composable
fun PoseListItem(pose: Pose = Pose(name = "Brass Sit")) {

    var heart = Icons.Default.FavoriteBorder
    if (pose.saved) {
        heart = Icons.Default.Favorite
    }

    Column (
        modifier = Modifier
            .background(color = DarkPink)
            .clickable{/* todo: wejście do podglądu */},
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = pose.name,
            color = AlmostWhite,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(15.dp))
        Image(
            painter = painterResource(id = pose.photoResId),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                /* todo: dodawanie polubienia */
                    PoseEvent.SavePose(pose)
                }
        ) {
            Icon(
                imageVector = heart,
                contentDescription = "Save pose",
                modifier = Modifier
                    .padding(15.dp)
                    .size(20.dp)
                    .align(Alignment.Center),
                tint = AlmostWhite,
            )
        }

    }
}

