package com.example.poleplanner.pose_detail_view

import com.example.poleplanner.data_structure.models.Pose
import com.example.poleplanner.data_structure.references.PoseWithTags

data class DetailState(
    val poseWithTags: PoseWithTags
        = PoseWithTags(Pose("placeholder"), listOf()),

    val descriptionEditing: Boolean = false,
    val notesEditing: Boolean = false,

    val descriptionOpen: Boolean = false,
    val notesOpen: Boolean = false
)