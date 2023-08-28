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
    fun getPosesWithTags(): List<PoseWithTags>

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
            "WHERE poseName = :poseName)")
    fun getTagsForPose(poseName: String): Flow<List<Tag>>

    // pobranie figur o danym tagu
    @Transaction
    @Query("SELECT * FROM pose " +
            "WHERE poseName IN " +
            "(SELECT poseName FROM PoseTagCrossRef " +
            "WHERE tagName = :tagName)")
    fun getPosesWithTag(tagName: String): Flow<List<Pose>>

    // pobranie figur o danych tagach
    @Transaction
    @Query("SELECT DISTINCT * FROM pose " +
            "INNER JOIN PoseTagCrossRef " +
            "ON pose.poseName = PoseTagCrossRef.poseName " +
            "WHERE PoseTagCrossRef.tagName IN (:tagNames)")
    fun getPosesWithTags(tagNames: Collection<String>): Flow<List<Pose>>

}