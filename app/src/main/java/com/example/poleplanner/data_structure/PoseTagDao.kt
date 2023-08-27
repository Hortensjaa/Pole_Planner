package com.example.poleplanner.data_structure

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface PoseTagDao {
    @Transaction
    @Query("SELECT * FROM pose")
    fun getPosesWithTags(): List<PoseWithTags>

    @Transaction
    @Query("SELECT * FROM tag")
    fun getTagsWithPoses(): List<TagWithPoses>

    // Insert a pose into the Pose table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPose(pose: Pose)

    // Insert a tag into the Tag table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: Tag)

    // Insert a pose-tag relationship into the PoseTagCrossRef table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoseTagCrossRef(crossRef: PoseTagCrossRef)

    @Transaction
    suspend fun insertPoseWithTags(pose: Pose, tags: List<Tag>) {
        insertPose(pose) // Insert the pose
        tags.forEach { tag ->
            insertTag(tag) // Insert each tag
            insertPoseTagCrossRef(PoseTagCrossRef(pose.poseName, tag.tagName)) // Create the relationship
        }
    }

//    @Transaction
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAllPosesWithTags(pose: Pose, tags: Collection<Tag>)
}