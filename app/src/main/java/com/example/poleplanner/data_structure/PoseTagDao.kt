package com.example.poleplanner.data_structure

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

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
    suspend fun getTagsForPose(poseName: String): List<Tag>

}