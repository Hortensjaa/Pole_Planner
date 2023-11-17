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
import androidx.compose.ui.unit.dp
import com.example.poleplanner.pose_detail_view.DetailEvent
import com.example.poleplanner.pose_detail_view.DetailState


@Composable
fun NotesContent (
    detailOnEvent: (DetailEvent) -> Unit,
    state: DetailState,
    scrollState: ScrollState
) {
    var updatedNotes by remember { mutableStateOf("") }
    LaunchedEffect(state.poseWithTags.pose) {
        updatedNotes = state.poseWithTags.pose.notes
    }

    ContentHideButton(
        text = "Notatki",
        action = { detailOnEvent(DetailEvent.NotesChangeVisibility) },
        isOpened = state.notesOpen,
        editable = true,
        editAction = { detailOnEvent(DetailEvent.NotesEditChange) },
        isEditing = state.notesEditing,
        saveAction = {
            detailOnEvent(DetailEvent.SaveNotes(updatedNotes))
            detailOnEvent(DetailEvent.NotesEditChange)
        }
    )
    LaunchedEffect(state.notesOpen, updatedNotes, state.notesEditing) {
        if (!state.notesOpen)
            scrollState.animateScrollTo(0)
        else
            scrollState.animateScrollTo(scrollState.maxValue)
    }
    if (state.notesOpen) {
        if (!state.notesEditing) {
            Column {
                Text(
                    text = state.poseWithTags.pose.notes,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(10.dp)
                )
            }
        } else {
            Column {
                BasicTextField(
                    value = updatedNotes,
                    onValueChange = { newText: String -> updatedNotes = newText },
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
