package com.example.poleplanner.poses_list_view

import com.example.poleplanner.data_structure.Difficulty
import com.example.poleplanner.data_structure.Pose

sealed interface PoseEvent {

    data class ChangeSave(
        val pose: Pose
    ): PoseEvent

    data class AddDiffFilter(
        val diff: Difficulty
    ) : PoseEvent

    data class DeleteDiffFilter(
        val diff: Difficulty
    ) : PoseEvent

    data class AddTagFilter(
        val tag: String
    ): PoseEvent

    data class DeleteTagFilter(
        val tag: String
    ): PoseEvent

    object ClearTagFilter: PoseEvent

    object ClearSearcher: PoseEvent

    // future/todo: filtrowanie po progressie
}