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

    // pobranie po id
    @Query("SELECT * FROM pose WHERE id = :id1")
    suspend fun getId(id1: Int): Pose?

    // sortowania
    @Query("SELECT * FROM pose ORDER BY name ASC")
    fun getByName(): Flow<List<Pose>>

       @Query("SELECT * FROM pose ORDER BY id ASC")
    fun getByID(): Flow<List<Pose>>

    // filtrowanie
    @Query("SELECT * FROM pose " +
            "WHERE difficulty = :diff " +
            "ORDER BY name ASC ")
    fun filterDifficulty(diff: Int): Flow<List<Pose>>

    @Query("SELECT * FROM pose " +
            "WHERE saved = :isSaved " +
            "ORDER BY name ASC ")
    fun filterSaved(isSaved: Boolean = true): Flow<List<Pose>>

    // wstawianie
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