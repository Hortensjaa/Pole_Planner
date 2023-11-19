package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.poleplanner.navbar.Screen
import com.example.poleplanner.pose_detail_view.DetailEvent
import com.example.poleplanner.pose_detail_view.DetailState


// todo: wyswietlanie kilku zdjec i mozliwosc zmiany dodanego przez uzytkownika
@Composable
fun PoseDetailScreen(
    poseName: String?,
    detailOnEvent: (DetailEvent) -> Unit,
    state: DetailState,
    navController: NavController,
) {
    val openDeleteDialog = remember { mutableStateOf(false) }

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
            if (openDeleteDialog.value) {
                DeleteDialog(
                    deleteAction = {
                        openDeleteDialog.value = false
                        detailOnEvent(DetailEvent.DeletePose)
                        navController.navigate(Screen.AllPosesScreen.route)
                    },
                    dismissAction = { openDeleteDialog.value = false }
                )
            }
            TagsRow(state.poseWithTags.tags)
            NameBar(
                state.poseWithTags.pose.poseName,
                state.poseWithTags.pose.saved,
                state.poseWithTags.pose.addedByUser,
                favAction = { detailOnEvent(DetailEvent.ChangeSave) },
                deleteAction = { openDeleteDialog.value = true }
            )
            PhotoBox(
                state.poseWithTags.pose.difficulty,
                state.poseWithTags.pose.photoResId,
                state.poseWithTags.pose.userPhoto
            )
            ProgressBar(state.poseWithTags.pose.progress) {
                p -> detailOnEvent(DetailEvent.SaveProgress(p))
            }
            DescriptionContent(
                detailOnEvent,
                state.poseWithTags.pose.addedByUser,
                state, scrollState
            )
            NotesContent(detailOnEvent, state, scrollState)
        }
    } else {
        Text("Nie znaleziono figury")
    }
}
