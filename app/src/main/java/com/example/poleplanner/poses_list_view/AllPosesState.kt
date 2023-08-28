package com.example.poleplanner.poses_list_view

import com.example.poleplanner.data_structure.Difficulty
import com.example.poleplanner.data_structure.Pose

data class AllPosesState(
    val poses: List<Pose> = emptyList(),
    val diffFilter: Difficulty? = null,
    val tagFilters: Collection<String> = emptyList()
)