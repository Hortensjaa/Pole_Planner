package com.example.poleplanner.pose_of_a_day

import com.example.poleplanner.data_structure.Pose

sealed interface DayEvent {

    object UncoverPose: DayEvent
    object CoverPose: DayEvent
    object GetNewPose: DayEvent
    data class ChangePose(
        val pose: Pose
    ): DayEvent
}