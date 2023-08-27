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

    // sortowania
    @Query("SELECT * FROM pose ORDER BY poseName ASC")
    fun sortByName(): Flow<List<Pose>>

    // filtrowanie
    @Query("SELECT * FROM pose " +
            "WHERE difficulty = :diff " +
            "ORDER BY poseName ASC ")
    fun filterDifficulty(diff: Difficulty): Flow<List<Pose>>

    @Query("SELECT * FROM pose " +
            "WHERE progress = :prog " +
            "ORDER BY poseName ASC ")
    fun filterProgress(prog: Progress): Flow<List<Pose>>

    @Query("SELECT * FROM pose " +
            "WHERE progress = :prog " +
            "AND difficulty = :diff " +
            "ORDER BY poseName ASC ")
    fun filterDifficultyAndProgress(
        diff: Difficulty,
        prog: Progress): Flow<List<Pose>>

    @Query("SELECT * FROM pose " +
            "WHERE saved = :isSaved " +
            "ORDER BY poseName ASC ")
    fun filterSaved(isSaved: Boolean = true): Flow<List<Pose>>

    // wstawianie
    @Insert
    suspend fun insert(pose: Pose)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(poses: Collection<Pose>)

    // aktualizacja
    @Update
    suspend fun update(pose: Pose)

    // zapisywanie i "odzapisywanie"
    @Transaction
    suspend fun savePose(pose: Pose) {
        pose.saved = true
        update(pose)
    }

    @Transaction
    suspend fun unsavePose(pose: Pose) {
        pose.saved = false
        update(pose)
    }

    // ustawianie postępu
    @Transaction
    suspend fun setProgress(pose: Pose, progress: Progress) {
        pose.progress = progress
        update(pose)
    }

}