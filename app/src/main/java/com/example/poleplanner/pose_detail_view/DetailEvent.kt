package com.example.poleplanner.pose_detail_view

import com.example.poleplanner.data_structure.Pose
import com.example.poleplanner.data_structure.Progress

sealed interface DetailEvent {

    data class ChangePose(
        val pose: Pose
    ): DetailEvent

    object ChangeSave: DetailEvent

    object NotesChangeVisibility : DetailEvent
    object NotesEditChange : DetailEvent
    data class SaveNotes(
        val notes: String
    ): DetailEvent

    object DescriptionChangeVisibility : DetailEvent

    data class SaveProgress(
        val progress: Progress
    ): DetailEvent

}