package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poleplanner.data_structure.Pose
import com.example.poleplanner.ui.theme.AlmostWhite
import com.example.poleplanner.ui.theme.AutoResizedText
import com.example.poleplanner.ui.theme.DarkPink
import com.example.poleplanner.ui.theme.TagBox
import com.example.poleplanner.ui.theme.Typography

@Preview
@Composable
fun PoseDetailView(
    pose: Pose = Pose(poseName = "Fireman Spin")
) {
    Column (
        modifier = Modifier
            .background(AlmostWhite)
            .padding(20.dp)
    )
    {
        NameBar(pose.poseName)
        TagsRow(listOf("statyczne", "duety"))
        Photo(photoResId = pose.photoResId)
        DescriptionContent(pose.description)
        NotesContent(pose.notes)
    }
}

@Composable
fun NameBar (
    poseName: String = "Pose name ph"
) {
    AutoResizedText(
        text = poseName,
        style = Typography.titleLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .wrapContentSize(Alignment.Center),
        color = DarkPink
    )
}

@Composable
fun TagsRow(
    tagNames: List<String> = listOf("statyczne", "duety")
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (tagNames.isNotEmpty()) {
            tagNames.forEach { tagName ->
                TagBox(
                    tagName = tagName,
                    backgroundColor =
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun Photo (
    photoResId: Int
) {
    Image(
        painter = painterResource(id = photoResId),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun DescriptionContent (
    description: String = ""
) {
    ContentHideButton(text = "Opis")
//    Text(
//        text = description,
//        modifier = Modifier.padding(10.dp))
}

@Composable
fun NotesContent (
    notes: String = ""
) {
    ContentHideButton(text = "Notatki")
//    Text(
//        text = notes,
//        modifier = Modifier.padding(10.dp))
}