package com.example.poleplanner.daoTests

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.poleplanner.data_structure.AppDatabase
import com.example.poleplanner.data_structure.daos.DayDao
import com.example.poleplanner.data_structure.models.Day
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class DaysTest {
    private lateinit var dayDao: DayDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        dayDao = db.getDayDaoInstance()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun testInsertAndGetLastDay() = runBlocking {
        val day = Day(
            dayId = 1,
            date = LocalDate.of(2023, 9, 7),
            poseOfDayName = "Test Pose",
            covered = true)
        dayDao.insertDay(day)
        val lastDay = dayDao.getLastDay()
        assertEquals(day, lastDay)
    }

    @Test
    @Throws(IOException::class)
    fun testCountDays() = runBlocking {
        val day1 = Day(
            dayId = 1,
            date = LocalDate.of(2023, 9, 7),
            poseOfDayName = "Test Pose 1",
            covered = true)
        val day2 = Day(
            dayId = 2,
            date = LocalDate.of(2023, 9, 8),
            poseOfDayName = "Test Pose 2",
            covered = true)
        dayDao.insertDay(day1)
        dayDao.insertDay(day2)
        val count = dayDao.countDays()
        assertEquals(2, count)
    }

    @Test
    @Throws(IOException::class)
    fun testUncoverDay() = runBlocking {
        val day = Day(
            dayId = 1,
            date = LocalDate.of(2023, 9, 7),
            poseOfDayName = "Test Pose",
            covered = true)
        dayDao.insertDay(day)
        dayDao.uncoverDay(day)
        val updatedDay = dayDao.getLastDay()
        assertEquals(false, updatedDay?.covered)
    }
}