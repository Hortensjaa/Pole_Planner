package com.example.poleplanner.pose_of_a_day

import com.example.poleplanner.data_structure.Pose
import java.time.LocalDateTime

data class DayState (
    val pose: Pose = Pose("Placeholder pose"), // figura na dziś
    val lastDrawDate: LocalDateTime = LocalDateTime.MIN, // dzień ostatniego "losowania"
    val covered: Boolean = true //  czy dzisiejsza figura jest jeszcze zasłonięta
)