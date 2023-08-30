package com.example.poleplanner.pose_detail_view

import com.example.poleplanner.data_structure.Pose

data class PoseDetailState(
    val pose: Pose = Pose("Placeholder pose"),
    val isEditing: Boolean = false,
    val descriptionOpen: Boolean = false,
    val notesOpen: Boolean = false
)