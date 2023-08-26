package com.example.poleplanner.all_poses_view

import com.example.poleplanner.data_structure.Difficulty
import com.example.poleplanner.data_structure.Pose

sealed interface PoseEvent {

    data class SavePose(
        val pose: Pose
    ): PoseEvent

    data class FilterByDiff(
        val diff: Difficulty
    ): PoseEvent

    object ClearDiffFilter: PoseEvent

//    data class FilterByProg(
//        val prog: Progress
//    ): PoseEvent
//
//    data class FilterByDiffAndProg(
//        val diff: Difficulty,
//        val prog: Progress
//    ): PoseEvent
}