package com.example.poleplanner.poses_list_view

import com.example.poleplanner.data_structure.Difficulty
import com.example.poleplanner.data_structure.Pose

data class AllPosesState(
//    val filtersVisible: Boolean = false,
    val poses: List<Pose> = emptyList(),
//    val poses_with_tags: Map<Pose, List<Tag>> = emptyMap(),
//    val sortType: SortType = SortType.NAME,
    val diffFilter: Difficulty? = null
//    val progFilter: Progress? = null
)