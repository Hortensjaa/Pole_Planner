package com.example.poleplanner.data_structure

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface PoseTagDao {

    // gettery
    @Transaction
    @Query("SELECT * FROM pose")
    fun filterPosesWithTags(): List<PoseWithTags>

    @Transaction
    @Query("SELECT * FROM tag")
    fun getTagsWithPoses(): List<TagWithPoses>

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
    fun getTagsForPose(poseName: String): Flow<List<Tag>>

    // pobranie figur o danym tagu
    @Transaction
    @Query("SELECT * FROM pose " +
            "WHERE poseName IN " +
            "(SELECT poseName FROM PoseTagCrossRef " +
            "WHERE tagName = :tagName " +
            "ORDER BY poseName ASC)")
    fun filterPosesWithTag(tagName: String): Flow<List<Pose>>

    // pobranie figur o danych tagach
    @Transaction
    @Query("SELECT DISTINCT p.* FROM pose p " +
            "WHERE (" +
            "   SELECT COUNT(*) FROM PoseTagCrossRef ptc " +
            "   WHERE ptc.poseName = p.poseName " +
            "   AND ptc.tagName IN (:tagNames)) = :tagCount " +
            "ORDER BY poseName ASC")
    fun filterPosesWithTags(tagNames: Collection<String>, tagCount: Int): Flow<List<Pose>>

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