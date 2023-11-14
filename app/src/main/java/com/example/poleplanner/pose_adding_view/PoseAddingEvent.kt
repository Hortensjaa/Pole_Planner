package com.example.poleplanner.pose_adding_view

import android.net.Uri
import com.example.poleplanner.data_structure.models.Difficulty

sealed interface PoseAddingEvent {
    data class SavePose (
        val name: String,
        val description: String,
        val diff: Difficulty,
        val photo: Uri,
    ) : PoseAddingEvent
}