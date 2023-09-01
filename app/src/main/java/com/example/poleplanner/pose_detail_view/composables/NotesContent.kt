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
import com.example.poleplanner.pose_detail_view.DetailViewModel
import com.example.poleplanner.pose_detail_view.PoseDetailState


@Composable
fun NotesContent (
    notes: String = "",
    detailVM: DetailViewModel,
    state: PoseDetailState,
    scrollState: ScrollState
) {
    var updatedNotes by remember { mutableStateOf(notes) }
    ContentHideButton(
        text = "Notatki",
        action = { detailVM.onEvent(DetailEvent.NotesChangeVisibility) },
        isOpened = state.notesOpen,
        editable = true,
        editAction = { detailVM.onEvent(DetailEvent.NotesEditChange) },
        isEditing = state.notesEditing,
        saveAction = {
            detailVM.onEvent(DetailEvent.SaveNotes(updatedNotes))
            detailVM.onEvent(DetailEvent.NotesEditChange)
        }
    )
    LaunchedEffect(state.notesOpen, updatedNotes, state.notesEditing) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }
    if (state.notesOpen) {
        if (!state.notesEditing) {
            Text(
                text = notes,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(10.dp)
            )
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
