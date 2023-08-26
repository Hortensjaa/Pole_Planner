package com.example.poleplanner.data_structure

data class AllPosesState (
    val poses: List<Pose> = emptyList(),
    val sortType: SortType = SortType.NAME
)