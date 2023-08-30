package com.example.poleplanner.pose_detail_view.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.poleplanner.pose_detail_view.DetailEvent
import com.example.poleplanner.pose_detail_view.DetailViewModel
import com.example.poleplanner.pose_detail_view.PoseDetailState


@Composable
fun NotesContent (
    notes: String = "",
    detailVM: DetailViewModel,
    state: PoseDetailState
) {
    ContentHideButton(
        text = "Notatki",
        action = { detailVM.onEvent(DetailEvent.changeNotes) },
        isOpened = state.notesOpen,
        editable = true
    )
    if (state.notesOpen) {
        Text(
            text = notes,
            modifier = Modifier.padding(10.dp))
    }
}