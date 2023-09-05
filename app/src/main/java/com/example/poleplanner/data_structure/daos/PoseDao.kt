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
import com.example.poleplanner.data_structure.models.Tag
import com.example.poleplanner.data_structure.references.PoseTagCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface PoseDao {

    @Query("SELECT * FROM pose WHERE poseName=:name")
    suspend fun getByName(name: String): Pose

    @Query("SELECT saved FROM pose WHERE poseName=:name")
    fun getSaveByName(name: String): Flow<Boolean>

    @Query("SELECT * FROM pose ORDER BY RANDOM() LIMIT 1")
    fun getRandomPose(): Pose?

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

    // wstawianie
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPose(pose: Pose)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: Tag)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoseTagCrossRef(crossRef: PoseTagCrossRef)

    @Transaction
    suspend fun insertPoseWithTags(pose: Pose, tags: List<Tag>) {
        insertPose(pose)
        tags.forEach {
                tag ->
            insertTag(tag)
            insertPoseTagCrossRef(PoseTagCrossRef(pose.poseName, tag.tagName))
        }
    }

    // pobranie tagów dla figury
    @Transaction
    @Query("SELECT * FROM tag " +
            "WHERE tagName IN " +
            "(SELECT tagName FROM PoseTagCrossRef " +
            "WHERE poseName = :poseName " +
            "ORDER BY tagName ASC)")
    fun getTagsForPose(poseName: String): List<Tag>

    @Transaction
    @Query("SELECT DISTINCT p.* FROM pose p " +
            "WHERE (" +
            "   SELECT COUNT(*) FROM PoseTagCrossRef ptc " +
            "   WHERE ptc.poseName = p.poseName " +
            "   AND ptc.tagName IN (:tagNames)) = :tagCount " +
            "AND difficulty IN (:diffs)" +
            "AND progress IN (:progress)" +
            "ORDER BY poseName ASC")
    fun filterAll(
        tagNames: Collection<String>,
        tagCount: Int,
        diffs: Collection<Difficulty>,
        progress: Collection<Progress>): Flow<List<Pose>>

    @Transaction
    @Query("SELECT DISTINCT p.* FROM pose p " +
            "WHERE (" +
            "   SELECT COUNT(*) FROM PoseTagCrossRef ptc " +
            "   WHERE ptc.poseName = p.poseName " +
            "   AND ptc.tagName IN (:tagNames)) = :tagCount " +
            "AND difficulty IN (:diffs) " +
            "AND progress IN (:progress) " +
            "AND saved = :savedOnly " +
            "ORDER BY poseName ASC")
    fun filterSaved(
        tagNames: Collection<String>,
        tagCount: Int,
        diffs: Collection<Difficulty>,
        progress: Collection<Progress>,
        savedOnly: Boolean = true): Flow<List<Pose>>

}