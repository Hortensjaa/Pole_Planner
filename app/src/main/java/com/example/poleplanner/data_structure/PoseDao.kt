package com.example.poleplanner.data_structure

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PoseDao {
    @Query("SELECT * FROM pose")
    suspend fun getAll(): List<Pose>

    @Query("SELECT * FROM pose ORDER BY name ASC")
    fun getPosesByName(): Flow<List<Pose>>

    @Query("SELECT * FROM pose WHERE id = :id1")
    suspend fun getId(id1: Int): Pose?

    @Query("SELECT * FROM pose WHERE saved = :isSaved")
    suspend fun getSaved(isSaved: Boolean = true): List<Pose>

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
    suspend fun setProgress(poseId: Int, progress: Int) {
        val pose = getId(poseId)
        if (pose != null) {
            pose.progress = progress
            update(pose)
        }
    }

    @Transaction
    suspend fun savePose(pose: Pose) {
        pose.saved = true
        update(pose)
    }

    @Transaction
    suspend fun setProgress(pose: Pose, progress: Int) {
        pose.progress = progress
        update(pose)
    }

}