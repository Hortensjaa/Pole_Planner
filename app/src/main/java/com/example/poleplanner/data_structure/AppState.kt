package com.example.poleplanner.data_structure

data class AppState (
    val poses: List<Pose> = InitialData.poses,
    val sortType: SortType = SortType.NAME
)