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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.poleplanner.pose_detail_view.DetailEvent
import com.example.poleplanner.pose_detail_view.DetailState


// todo:
// opcja usuwania i edytowania figur dodanych przez użytkownika
// wyswietlanie zdjec
@Composable
fun PoseDetailScreen(
    poseName: String?,
    detailOnEvent: (DetailEvent) -> Unit,
    state: DetailState
) {
    if (poseName != null) {
        val scrollState = rememberScrollState(0)
        LaunchedEffect(Unit) {
            detailOnEvent(DetailEvent.ChangePose(poseName))
            if (state.descriptionOpen) detailOnEvent(DetailEvent.DescriptionChangeVisibility)
            if (state.notesOpen) detailOnEvent(DetailEvent.NotesChangeVisibility)
        }
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                .verticalScroll(scrollState)
        ) {
            TagsRow(state.poseWithTags.tags)
            NameBar(state.poseWithTags.pose.poseName, state.poseWithTags.pose.saved) {
                detailOnEvent(DetailEvent.ChangeSave)
            }
            Photo(photoResId = state.poseWithTags.pose.photoResId)
            ProgressBar(state.poseWithTags.pose.progress) {
                p -> detailOnEvent(DetailEvent.SaveProgress(p))
            }
            DescriptionContent(state.poseWithTags.pose.description, state, scrollState) {
                detailOnEvent(DetailEvent.DescriptionChangeVisibility)
            }
            NotesContent(detailOnEvent, state, scrollState)
        }
    } else {
        Text("Nie znaleziono figury")
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
