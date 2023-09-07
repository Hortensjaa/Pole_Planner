package com.example.poleplanner.daoTests

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.poleplanner.data_structure.AppDatabase
import com.example.poleplanner.data_structure.daos.PoseDao
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
class EditingFieldsTest {
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
    fun insertAndGetPoseTest() {
        runBlocking {
            val pose = Pose("Test pose")
            poseDao.insertPose(pose)
            val retrievedPose = poseDao.getByName(pose.poseName)
            Assert.assertEquals(pose, retrievedPose)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testSaveAndUnsavePose() {
        runBlocking {
            val pose = Pose("Test Pose", saved = false)
            poseDao.insertPose(pose)

            poseDao.savePose(pose)
            val isSaved = poseDao.getSaveByName("Test Pose").first()
            Assert.assertTrue(isSaved)

            poseDao.unsavePose(pose)
            val isUnsaved = poseDao.getSaveByName("Test Pose").first()
            Assert.assertFalse(isUnsaved)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testSetProgress() {
        runBlocking {
            val pose = Pose("Test Pose")
            poseDao.insertPose(pose)
            val progress = Progress.PERFECT
            poseDao.setProgress(pose, progress)
            val retrievedPose = poseDao.getByName("Test Pose")
            Assert.assertEquals(progress, retrievedPose.progress)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testSetNotes() {
        runBlocking {
            val pose = Pose("Test Pose")
            poseDao.insertPose(pose)
            val notes = "Test notes"
            poseDao.setNotes(pose, notes)
            val retrievedPose = poseDao.getByName("Test Pose")
            Assert.assertEquals(notes, retrievedPose.notes)
        }
    }
}

