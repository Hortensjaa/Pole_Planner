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
import com.example.poleplanner.data_structure.references.PoseWithTags
import kotlinx.coroutines.flow.Flow

@Dao
interface PoseDao {

    @Query("SELECT * FROM pose WHERE poseName=:name")
    suspend fun getByName(name: String): Pose

    @Query("DELETE FROM pose WHERE poseName=:name")
    suspend fun deleteByName(name: String)

    @Query("SELECT * FROM tag ORDER BY tagName ASC")
    fun getAllTagsFlow(): Flow<List<Tag>>

    @Query("SELECT * FROM tag ORDER BY tagName ASC")
    fun getAllTags(): List<Tag>

    @Transaction
    @Query("SELECT * FROM pose p " +
            "LEFT JOIN PoseTagCrossRef ptc ON p.poseName = ptc.poseName " +
            "LEFT JOIN tag t ON ptc.tagName = t.tagName " +
            "WHERE p.poseName = :name")
    suspend fun getPoseWithTagsByName(name: String): PoseWithTags


    @Query("SELECT saved FROM pose WHERE poseName=:name")
    fun getSaveByName(name: String): Flow<Boolean>

    @Query("SELECT * FROM pose " +
            "WHERE addedByUser = :f " +
            "ORDER BY RANDOM() LIMIT 1")
    fun getRandomPose(f: Boolean = false): Pose?

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

    // zapisywanie opisu
    @Transaction
    suspend fun setDescription(pose: Pose, text: String) {
        pose.description = text
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

    // filtrowanie list z tagami
    @Transaction
    @Query("SELECT DISTINCT p.* FROM pose p " +
            "INNER JOIN PoseTagCrossRef ptc ON p.poseName = ptc.poseName " +
            "INNER JOIN tag t ON ptc.tagName = t.tagName " +
            "ORDER BY p.poseName ASC")
    fun getAllPosesWithTags(): Flow<List<PoseWithTags>>

    @Transaction
    @Query("SELECT DISTINCT p.* FROM pose p " +
            "LEFT  JOIN PoseTagCrossRef ptc ON p.poseName = ptc.poseName " +
            "LEFT  JOIN tag t ON ptc.tagName = t.tagName " +
            "WHERE (" +
            "   SELECT COUNT(*) FROM PoseTagCrossRef ptc " +
            "   WHERE ptc.poseName = p.poseName " +
            "   AND ptc.tagName IN (:tagNames)) = :tagCount " +
            "AND difficulty IN (:diffs) " +
            "AND progress IN (:progress) " +
            "AND addedByUser IN (:addedOnly) " +
            "ORDER BY poseName ASC")
    fun filterPosesWithTags(
        tagNames: Collection<String> = listOf(),
        tagCount: Int = 0,
        diffs: Collection<Difficulty> = Difficulty.values().asList(),
        progress: Collection<Progress> = Progress.values().asList(),
        addedOnly: Collection<Boolean> = listOf(true, false)
    ): Flow<List<PoseWithTags>>

    @Transaction
    @Query("SELECT DISTINCT p.* FROM pose p " +
            "LEFT  JOIN PoseTagCrossRef ptc ON p.poseName = ptc.poseName " +
            "LEFT  JOIN tag t ON ptc.tagName = t.tagName " +
            "WHERE (" +
            "   SELECT COUNT(*) FROM PoseTagCrossRef ptc " +
            "   WHERE ptc.poseName = p.poseName " +
            "   AND ptc.tagName IN (:tagNames)) = :tagCount " +
            "AND difficulty IN (:diffs) " +
            "AND progress IN (:progress) " +
            "AND addedByUser IN (:addedOnly) " +
            "AND saved = :savedOnly " +
            "ORDER BY poseName ASC")
    fun filterPosesWithTagsSaved(
        tagNames: Collection<String> = listOf(),
        tagCount: Int = 0,
        diffs: Collection<Difficulty> = Difficulty.values().asList(),
        progress: Collection<Progress> = Progress.values().asList(),
        addedOnly: Collection<Boolean> = listOf(true, false),
        savedOnly: Boolean = true
    ): Flow<List<PoseWithTags>>

}