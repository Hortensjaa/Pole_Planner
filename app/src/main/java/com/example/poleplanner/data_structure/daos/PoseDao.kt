package com.example.poleplanner.data_structure.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.poleplanner.data_structure.models.Difficulty
import com.example.poleplanner.data_structure.models.Pose
import com.example.poleplanner.data_structure.models.Progress
import kotlinx.coroutines.flow.Flow

@Dao
interface PoseDao {

    @Query("SELECT * FROM pose WHERE poseName=:name")
    suspend fun getByName(name: String): Pose

    @Query("SELECT * FROM pose WHERE poseName=:name")
    fun getByNameAsync(name: String): Flow<Pose>

    @Query("SELECT saved FROM pose WHERE poseName=:name")
    fun getSaveByName(name: String): Flow<Boolean>

    @Query("SELECT * FROM pose ORDER BY RANDOM() LIMIT 1")
    fun getRandomPose(): Pose?

    // zliczanie
    @Query("SELECT COUNT(*) FROM pose")
    fun countPoses(): Flow<Int>

    // sortowania
    @Query("SELECT * FROM pose ORDER BY poseName ASC")
    fun sortByName(): List<Pose>

    // filtrowanie
    @Query("SELECT * FROM pose " +
            "WHERE difficulty = :diff " +
            "ORDER BY poseName ASC ")
    fun filterDifficulty(diff: Difficulty): Flow<List<Pose>>

    @Query("SELECT * FROM pose " +
            "WHERE difficulty IN (:diffs) " +
            "ORDER BY poseName ASC ")
    fun filterDifficultyList(diffs: Collection<Difficulty>): Flow<List<Pose>>

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
        prog: Progress
    ): Flow<List<Pose>>

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

    // zapisywanie notatek
    @Transaction
    suspend fun setNotes(pose: Pose, notes: String) {
        pose.notes = notes
        update(pose)
    }

}