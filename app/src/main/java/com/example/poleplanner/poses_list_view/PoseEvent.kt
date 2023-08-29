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

    data class AddTagFilter(
        val tag: String
    ): PoseEvent

    data class DeleteTagFilter(
        val tag: String
    ): PoseEvent

    object ClearTagFilter: PoseEvent

    // future/todo: filtrowanie po progressie
}