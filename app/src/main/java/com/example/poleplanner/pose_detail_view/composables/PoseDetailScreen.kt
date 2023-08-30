package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.poleplanner.data_structure.Tag
import com.example.poleplanner.pose_detail_view.DetailViewModel
import com.example.poleplanner.pose_detail_view.PoseDetailState
import com.example.poleplanner.ui.theme.AlmostWhite
import com.example.poleplanner.ui.theme.AutoResizedText
import com.example.poleplanner.ui.theme.DarkPink
import com.example.poleplanner.ui.theme.TagBox
import com.example.poleplanner.ui.theme.Typography

// todo: zmienić kolory na themowe
@Composable
fun PoseDetailScreen(
    poseName: String?,
    detailVM: DetailViewModel,
    state: PoseDetailState
) {
    if (poseName != null) {
        var pose by remember { mutableStateOf<Pose?>(null) }
        LaunchedEffect(poseName) {
             pose = detailVM.getPoseByName(poseName)
        }
        if (pose != null) {
            val poseTags by detailVM.PTdao.getTagsForPose(poseName)
                .collectAsState(emptyList())
            Column (
                modifier = Modifier
                    .background(AlmostWhite)
                    .padding(20.dp)
            ) {
                NameBar(pose!!.poseName)
                TagsRow(poseTags)
                Photo(photoResId = pose!!.photoResId)
                DescriptionContent(pose!!.description)
                NotesContent(pose!!.notes)
            }
        }

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
    tags: List<Tag>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (tags.isNotEmpty()) {
            tags.forEach { tag ->
                TagBox(
                    tagName = tag.tagName,
                    backgroundColor = DarkPink.copy(alpha = 0.7f),
                    textColor = AlmostWhite
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