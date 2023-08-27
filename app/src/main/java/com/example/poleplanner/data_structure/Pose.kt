package com.example.poleplanner.data_structure

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.poleplanner.R

@Entity
data class Pose(
//    @PrimaryKey(autoGenerate = true) val poseId: Int = 0,
    @PrimaryKey val poseName: String,
    val description: String =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
        "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis " +
        "nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
        "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu " +
        "fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt " +
        "in culpa qui officia deserunt mollit anim id est laborum.",
    val photoResId: Int = R.drawable.pd, // future: opcja dodania własnego zdjęcia zamiast rysunku
    val difficulty: Difficulty = Difficulty.BEGGINER, // fixme: usunąć tu default
    var saved: Boolean = false,
    var progress: Progress = Progress.JESZCZE_NIE,
    var notes: String = ""
)

enum class Progress {
    JESZCZE_NIE,
    PRAWIE,
    ZROBIONE,
    IDEALNIE
}

enum class Difficulty {
    BEGGINER,
    INTERMEDIATE,
    ADVANCED
}


