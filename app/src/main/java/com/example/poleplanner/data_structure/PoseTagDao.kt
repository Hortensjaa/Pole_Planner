package com.example.poleplanner.data_structure

import androidx.room.Dao
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
}