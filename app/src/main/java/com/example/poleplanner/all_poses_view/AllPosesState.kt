package com.example.poleplanner.all_poses_view

import com.example.poleplanner.data_structure.Difficulty
import com.example.poleplanner.data_structure.Pose
import com.example.poleplanner.data_structure.Progress

data class AllPosesState(
    val poses: List<Pose> = emptyList(),
//    val sortType: SortType = SortType.NAME,
    val diffFilter: Difficulty? = null,
//    val progFilter: Progress? = null
)