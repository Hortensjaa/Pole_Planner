package com.example.poleplanner.daoTests

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.poleplanner.data_structure.AppDatabase
import com.example.poleplanner.data_structure.daos.PoseDao
import com.example.poleplanner.data_structure.models.Difficulty
import com.example.poleplanner.data_structure.models.Pose
import com.example.poleplanner.data_structure.models.Progress
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class FilteringTest {
    private lateinit var poseDao: PoseDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        poseDao = db.getPoseDaoInstance()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testDifficulty() {
        runBlocking {
            val pose1 = Pose("Pose1", difficulty = Difficulty.BEGINNER)
            val pose2 = Pose("Pose2", difficulty = Difficulty.INTERMEDIATE)
            poseDao.insertPoseWithTags(pose1, listOf())
            poseDao.insertPoseWithTags(pose2, listOf())

            val posesWithTags1 = poseDao.filterPosesWithTags(
                diffs = listOf(Difficulty.BEGINNER, Difficulty.INTERMEDIATE)
            ).first()
            Assert.assertEquals(2, posesWithTags1.size)

            val posesWithTags2 = poseDao.filterPosesWithTags(
                diffs = listOf(Difficulty.BEGINNER)
            ).first()
            Assert.assertEquals(1, posesWithTags2.size)

            val posesWithTags3 = poseDao.filterPosesWithTags(
                diffs = listOf(Difficulty.ADVANCED)
            ).first()
            Assert.assertEquals(0, posesWithTags3.size)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testProgress() {
        runBlocking {
            val pose1 = Pose("Pose1", progress = Progress.ALMOST)
            val pose2 = Pose("Pose2", progress = Progress.PERFECT)
            poseDao.insertPoseWithTags(pose1, listOf())
            poseDao.insertPoseWithTags(pose2, listOf())

            val posesWithTags1 = poseDao.filterPosesWithTags().first()
            Assert.assertEquals(2, posesWithTags1.size)

            val posesWithTags2 = poseDao.filterPosesWithTags(
                progress = listOf(Progress.PERFECT)
            ).first()
            Assert.assertEquals(1, posesWithTags2.size)

            val posesWithTags3 = poseDao.filterPosesWithTags(
                progress = listOf(Progress.NOT_YET)
            ).first()
            Assert.assertEquals(0, posesWithTags3.size)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testSaved() {
        runBlocking {
            val pose1 = Pose("Pose1", saved = true)
            val pose2 = Pose("Pose2", saved = false)
            poseDao.insertPoseWithTags(pose1, listOf())
            poseDao.insertPoseWithTags(pose2, listOf())

            val posesWithTags1 = poseDao.filterPosesWithTags().first()
            Assert.assertEquals(2, posesWithTags1.size)

            val posesWithTags2 = poseDao.filterPosesWithTagsSaved().first()
            Assert.assertEquals(1, posesWithTags2.size)
        }
    }

}
