package com.example.poleplanner.pose_adding_view

import android.net.Uri
import com.example.poleplanner.data_structure.models.Difficulty
import com.example.poleplanner.data_structure.models.Tag

sealed interface PoseAddingEvent {
    data class SavePose (
        val name: String,
        val description: String,
        val diff: Difficulty,
        val photo: Uri,
    ) : PoseAddingEvent

    data class AddSelectedTag(
        val tag: Tag
    ): PoseAddingEvent

    data class DeleteSelectedTag(
        val tag: Tag
    ): PoseAddingEvent

    data class UpdateTags(
        val tag: Tag
    ): PoseAddingEvent

    object ClearTags: PoseAddingEvent
}