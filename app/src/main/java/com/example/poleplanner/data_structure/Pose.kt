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
    val difficulty: Int = 0,
    var saved: Boolean = false,
    var progress: Int = 0
    // future: dodać hasztagi np. szpagat, odwrócone itd
)

@Dao
interface PoseDao {
    @Query("SELECT * FROM pose")
    suspend fun getAll(): List<Pose>

    @Query("SELECT * FROM pose WHERE id = :id1")
    suspend fun getId(id1: Int): Pose?

    @Insert
    suspend fun insert(pose: Pose)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(poses: List<Pose>)

    @Update
    suspend fun update(pose: Pose)

    @Transaction
    suspend fun savePose(poseId: Int) {
        val pose = getId(poseId)
        if (pose != null) {
            pose.saved = true
            update(pose)
        }
    }

    @Transaction
    suspend fun checkProgress(poseId: Int, progress: Int) {
        val pose = getId(poseId)
        if (pose != null) {
            pose.progress = progress
            update(pose)
        }
    }
    
    // Add other operations like update and delete as needed
}
