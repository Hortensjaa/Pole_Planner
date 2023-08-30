package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.poleplanner.data_structure.Pose
import com.example.poleplanner.pose_detail_view.DetailEvent
import com.example.poleplanner.pose_detail_view.DetailViewModel
import com.example.poleplanner.pose_detail_view.PoseDetailState
import com.example.poleplanner.ui.theme.AlmostWhite

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
            detailVM.onEvent(DetailEvent.ChangePose(pose!!))
        }
        if (pose != null) {
            val poseTags by detailVM.PTdao.getTagsForPose(poseName)
                .collectAsState(emptyList())
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(AlmostWhite)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                TagsRow(poseTags)
                NameBar(pose!!.poseName, pose!!.saved) { detailVM.onEvent(DetailEvent.ChangeSave) }
                Photo(photoResId = pose!!.photoResId)
                ProgressRow(pose!!.progress, detailVM)
                DescriptionContent(pose!!.description, detailVM, state)
                NotesContent(pose!!.notes, detailVM, state)
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
    description: String = "",
    detailVM: DetailViewModel,
    state: PoseDetailState
) {
    ContentHideButton(
        text = "Opis",
        action = { detailVM.onEvent(DetailEvent.DescriptionChangeVisibility) },
        isOpened = state.descriptionOpen
    )
    if (state.descriptionOpen) {
        Text(
            text = description,
            modifier = Modifier.padding(10.dp))
    }
}