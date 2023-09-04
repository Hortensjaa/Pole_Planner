package com.example.poleplanner.data_structure.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.poleplanner.data_structure.models.Tag
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    // sortowania
    @Query("SELECT * FROM tag ORDER BY tagName ASC")
    fun sortByName(): Flow<List<Tag>>

    // wstawianie
    @Insert
    suspend fun insert(tag: Tag)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(poses: List<Tag>)

}