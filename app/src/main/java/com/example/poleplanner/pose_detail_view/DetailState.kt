package com.example.poleplanner.pose_detail_view

import com.example.poleplanner.data_structure.models.Pose
import com.example.poleplanner.data_structure.references.PoseWithTags

data class DetailState(
//    val pose: Pose = Pose("Placeholder pose"),
    val poseWithTags: PoseWithTags
        = PoseWithTags(Pose("placeholder"), listOf()),
    val notesEditing: Boolean = false,
    val descriptionOpen: Boolean = false,
    val notesOpen: Boolean = false
)