package com.example.poleplanner.data_structure.models

import androidx.room.Entity
import androidx.room.PrimaryKey

// future: dodawanie własnych hashtagów
@Entity
data class Tag (
//    @PrimaryKey(autoGenerate = true) val tagId: Int = 0,
    @PrimaryKey val tagName: String = "Statyczne",
    val description: String =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
        "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis " +
        "nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
        "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu " +
        "fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt " +
        "in culpa qui officia deserunt mollit anim id est laborum.",
)
