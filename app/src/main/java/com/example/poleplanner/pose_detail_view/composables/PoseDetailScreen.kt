package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
            if (state.descriptionOpen) detailVM.onEvent(DetailEvent.DescriptionChangeVisibility)
            if (state.notesOpen) detailVM.onEvent(DetailEvent.NotesChangeVisibility)

        }
        if (pose != null) {
            val scrollState = rememberScrollState()
            val poseTags by detailVM.PTdao.getTagsForPose(poseName)
                .collectAsState(emptyList())
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                    .verticalScroll(scrollState)
            ) {
                TagsRow(poseTags)
                NameBar(pose!!.poseName, pose!!.saved) {
                    detailVM.onEvent(DetailEvent.ChangeSave)
                }
                Photo(photoResId = pose!!.photoResId)
                ProgressRow(pose!!.progress, detailVM)
                DescriptionContent(pose!!.description, state, scrollState) {
                    detailVM.onEvent(DetailEvent.DescriptionChangeVisibility)
                }
                NotesContent(pose!!.notes, detailVM, state, scrollState)
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
