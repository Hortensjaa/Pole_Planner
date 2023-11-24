package com.example.poleplanner.poses_list_view

import com.example.poleplanner.data_structure.models.Difficulty
import com.example.poleplanner.data_structure.models.Pose
import com.example.poleplanner.data_structure.models.Progress

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

    data class ChangeTagFilter(
        val tag: String
    ): PoseEvent

    data class AddProgressFilter(
        val prog: Progress
    ): PoseEvent

    data class DeleteProgressFilter(
        val prog: Progress
    ): PoseEvent

    data class ClearState(
        val savedOnly: Boolean
    ): PoseEvent

    object ClearTagFilter: PoseEvent

    object ChangeAddedFilter: PoseEvent

    object ClearSearcher: PoseEvent

    data class OnSearchTextChange(
        val text: String
    ): PoseEvent
}