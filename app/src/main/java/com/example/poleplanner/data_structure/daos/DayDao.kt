package com.example.poleplanner.data_structure.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.poleplanner.data_structure.models.Day

@Dao
interface DayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDay(day: Day)

    @Query("SELECT * FROM day " +
            "ORDER BY dayId DESC " +
            "LIMIT 1")
    suspend fun getLastDay() : Day?

    @Query("SELECT COUNT(*) FROM day")
    suspend fun countDays() : Int

    @Update
    suspend fun update(day: Day)

    @Transaction
    suspend fun uncoverDay(day: Day) {
        day.covered = false
        update(day)
    }
}