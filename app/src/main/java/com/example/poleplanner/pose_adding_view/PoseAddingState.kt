package com.example.poleplanner.pose_adding_view

import com.example.poleplanner.data_structure.models.Tag

data class PoseAddingState (
    val allTags: List<Tag> = emptyList(),
    val selectedTags: List<Tag> = emptyList(),
)