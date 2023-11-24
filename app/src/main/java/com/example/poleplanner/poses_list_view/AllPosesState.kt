package com.example.poleplanner.poses_list_view

import com.example.poleplanner.data_structure.models.Difficulty
import com.example.poleplanner.data_structure.models.Progress
import com.example.poleplanner.data_structure.models.Tag
import com.example.poleplanner.data_structure.references.PoseWithTags

data class AllPosesState(
    val posesWithTags: List<PoseWithTags> = emptyList(),
    val allTags: List<Tag> = emptyList(),

    val diffFilters: Collection<Difficulty> = Difficulty.values().toList(),
    val tagFilters: Collection<String> = emptyList(),
    val progressFilters: Collection<Progress> = Progress.values().toList(),
    val searchText: String = "",

    val savedOnly: Boolean = false,

    val addedByUserOnly: Boolean = false
)