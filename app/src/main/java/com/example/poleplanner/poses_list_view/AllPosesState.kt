package com.example.poleplanner.poses_list_view

import com.example.poleplanner.data_structure.Difficulty
import com.example.poleplanner.data_structure.Pose

data class AllPosesState(
    val poses: List<Pose> = emptyList(),
    val diffFilters: Collection<Difficulty> = Difficulty.values().toList(),
    val tagFilters: Collection<String> = emptyList(),
    val searchText: String = ""
)