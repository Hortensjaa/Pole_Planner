package com.example.poleplanner.data_structure

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.poleplanner.R

@Entity
data class Pose(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
        "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis " +
        "nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
        "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu " +
        "fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt " +
        "in culpa qui officia deserunt mollit anim id est laborum.",
    val photoResId: Int = R.drawable.pd, // future: opcja dodania własnego zdjęcia zamiast rysunku
    val difficulty: Int = 0, // 0 - 2
    var saved: Boolean = false,
    var progress: Int = 0
    // future: dodać hasztagi np. szpagat, odwrócone itd
)

