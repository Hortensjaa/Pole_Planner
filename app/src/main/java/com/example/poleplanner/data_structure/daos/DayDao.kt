package com.example.poleplanner.data_structure.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.poleplanner.data_structure.models.Day

@Dao
interface DayDao {

//    @Query("SELECT * FROM day WHERE dateTime = :dateTime")
//    fun getDay(dateTime: LocalDateTime)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDay(day: Day)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertPoseWithDay(crossRef: PoseWithDay)
}