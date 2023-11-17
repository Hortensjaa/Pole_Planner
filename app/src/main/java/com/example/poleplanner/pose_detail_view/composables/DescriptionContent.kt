package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.dp
import com.example.poleplanner.pose_detail_view.DetailEvent
import com.example.poleplanner.pose_detail_view.DetailState
import kotlin.math.roundToInt


@Composable
fun DescriptionContent (
    detailOnEvent: (DetailEvent) -> Unit,
    addedByUser: Boolean = false,
    state: DetailState,
    scrollState: ScrollState
) {
    var updatedDescription by remember { mutableStateOf("") }
    LaunchedEffect(state.poseWithTags.pose) {
        updatedDescription = state.poseWithTags.pose.description
    }

    var scrollToPosition by remember { mutableStateOf(0F) }
    ContentHideButton(
        text = "Opis",
        action = { detailOnEvent(DetailEvent.DescriptionChangeVisibility) },
        isOpened = state.descriptionOpen,
        editable = addedByUser,
        editAction = { detailOnEvent(DetailEvent.DescriptionEditChange) },
        isEditing = state.descriptionEditing,
        saveAction = {
            detailOnEvent(DetailEvent.SaveDescription(updatedDescription))
            detailOnEvent(DetailEvent.DescriptionEditChange)
        }
    )
    LaunchedEffect(state.descriptionOpen) {
        if (state.descriptionOpen)
            scrollState.animateScrollTo(scrollToPosition.roundToInt())
        else
            scrollState.animateScrollTo(0)
    }
    if (state.descriptionOpen) {
        if (!state.descriptionEditing) {
            Text(
                text = updatedDescription,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(10.dp)
                    .onGloballyPositioned { coordinates ->
                        scrollToPosition = coordinates.positionInRoot().y
                    }
            )
        } else {
            Column {
                BasicTextField(
                    value = updatedDescription,
                    onValueChange = { newText: String -> updatedDescription = newText },
                    minLines = 1,
                    maxLines = 10,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(10.dp)
                )
            }
        }
    }
}
