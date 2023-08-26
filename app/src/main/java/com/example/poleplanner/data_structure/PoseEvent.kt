package com.example.poleplanner.data_structure

sealed interface PoseEvent {

    data class SavePose(
        val pose: Pose): PoseEvent

    data class SortPoses(
        val sortType: SortType): PoseEvent
}