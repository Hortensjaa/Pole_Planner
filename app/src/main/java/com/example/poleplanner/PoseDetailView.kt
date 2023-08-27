package com.example.poleplanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poleplanner.data_structure.Pose

@Preview
@Composable
fun PoseDetailView(pose: Pose = Pose(name = "Pose 1")) {
    Text(text = pose.name, fontSize = 16.sp, modifier = Modifier.padding(16.dp))
    Text(text = pose.description, fontSize = 14.sp, modifier = Modifier.padding(16.dp))

    val imageModifier = Modifier
        .size(200.dp) // Adjust size as needed
        .fillMaxWidth()

    Image(
        painter = painterResource(id = pose.photoResId), // Use painterResource
        contentDescription = null, // Add a meaningful description
        modifier = imageModifier,
        contentScale = ContentScale.Crop // Adjust scaling as needed
    )
}