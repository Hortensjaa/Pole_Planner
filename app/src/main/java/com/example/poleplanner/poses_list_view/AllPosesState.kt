package com.example.poleplanner.poses_list_view

import com.example.poleplanner.data_structure.models.Difficulty
import com.example.poleplanner.data_structure.models.Pose
import com.example.poleplanner.data_structure.models.Progress

data class AllPosesState(
    val poses: List<Pose> = emptyList(),
    val diffFilters: Collection<Difficulty> = Difficulty.values().toList(),
    val tagFilters: Collection<String> = emptyList(),
    val progressFilters: Collection<Progress> = emptyList(),
    val searchText: String = "",
    val savedOnly: Boolean = false
)