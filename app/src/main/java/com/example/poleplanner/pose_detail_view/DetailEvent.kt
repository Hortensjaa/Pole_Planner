package com.example.poleplanner.pose_detail_view

import com.example.poleplanner.data_structure.models.Progress

sealed interface DetailEvent {

    data class ChangePose(
        val poseName: String,
    ): DetailEvent

    object ChangeSave: DetailEvent

    object NotesChangeVisibility : DetailEvent
    object NotesEditChange : DetailEvent
    data class SaveNotes(
        val notes: String
    ): DetailEvent

    object DescriptionChangeVisibility : DetailEvent
    object DescriptionEditChange : DetailEvent
    data class SaveDescription(
        val description: String
    ): DetailEvent

    data class SaveProgress(
        val progress: Progress
    ): DetailEvent

    object DeletePose : DetailEvent

}