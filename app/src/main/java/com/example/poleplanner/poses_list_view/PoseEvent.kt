package com.example.poleplanner.poses_list_view

import com.example.poleplanner.data_structure.Difficulty
import com.example.poleplanner.data_structure.Pose

sealed interface PoseEvent {

    data class ChangeSave(
        val pose: Pose
    ): PoseEvent

    data class FilterByDiff(
        val diff: Difficulty
    ): PoseEvent

    object ClearDiffFilter: PoseEvent

    // future/todo: filtrowanie po progressie
}