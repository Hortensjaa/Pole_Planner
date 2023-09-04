package com.example.poleplanner.data_structure.references

import androidx.room.Embedded
import androidx.room.Relation
import com.example.poleplanner.data_structure.models.Day
import com.example.poleplanner.data_structure.models.Pose

data class PoseWithDay(
    @Embedded val pose: Pose,
    @Relation(
        parentColumn = "poseName",
        entityColumn = "poseOfDayName"
    )
    val days: List<Day>
)