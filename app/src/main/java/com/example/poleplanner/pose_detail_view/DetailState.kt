package com.example.poleplanner.pose_detail_view

import com.example.poleplanner.data_structure.models.Pose

data class DetailState(
    val pose: Pose = Pose("Placeholder pose"),
    val notesEditing: Boolean = false,
    val descriptionOpen: Boolean = false,
    val notesOpen: Boolean = false
)