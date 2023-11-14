package com.example.poleplanner.daoTests

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.poleplanner.data_structure.AppDatabase
import com.example.poleplanner.data_structure.daos.PoseDao
import com.example.poleplanner.data_structure.models.Pose
import com.example.poleplanner.data_structure.models.Tag
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TagsTest {
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
    fun testGetAllTags() {
        runBlocking {
            val tag1 = Tag("cTag1")
            val tag2 = Tag("aTag2")
            val tag3 = Tag("bTag3")

            poseDao.insertTag(tag1)
            poseDao.insertTag(tag2)
            poseDao.insertTag(tag3)

            val allTags = poseDao.getAllTagsFlow().first()

            Assert.assertEquals(3, allTags.size)
            Assert.assertEquals( tag2.tagName, allTags[0].tagName)
            Assert.assertEquals(tag3.tagName, allTags[1].tagName)
            Assert.assertEquals(tag1.tagName, allTags[2].tagName)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testInsertPoseWithTags() {
        runBlocking {
            val pose = Pose("Test Pose")
            val tags = listOf(Tag("Tag1"), Tag("Tag2"))
            poseDao.insertPoseWithTags(pose, tags)

            val retrievedTags = poseDao.getTagsForPose("Test Pose")
            Assert.assertEquals(2, retrievedTags.size)

            val tagNames = retrievedTags.map { it.tagName }
            Assert.assertTrue(tagNames.contains("Tag1"))
            Assert.assertTrue(tagNames.contains("Tag2"))
        }
    }

    @Test
    @Throws(Exception::class)
    fun testGetAllPosesWithTags() {
        runBlocking {
            val pose1 = Pose("Pose1")
            val pose2 = Pose("Pose2")
            val tags1 = listOf(Tag("Tag1"), Tag("Tag2"))
            val tags2 = listOf(Tag("Tag2"), Tag("Tag3"))
            poseDao.insertPoseWithTags(pose1, tags1)
            poseDao.insertPoseWithTags(pose2, tags2)

            val posesWithTags = poseDao.getAllPosesWithTags().first()
            Assert.assertEquals(2, posesWithTags.size)
            Assert.assertEquals(tags1, posesWithTags[0].tags)
            Assert.assertEquals(tags2, posesWithTags[1].tags)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testFilterTags() {
        runBlocking {
            val pose1 = Pose("Pose1")
            val pose2 = Pose("Pose2")
            val pose3 = Pose("Pose3")
            val tags1 = listOf(Tag("Tag1"), Tag("Tag2"))
            val tags2 = listOf(Tag("Tag2"), Tag("Tag3"))
            poseDao.insertPoseWithTags(pose1, tags1)
            poseDao.insertPoseWithTags(pose2, tags2)
            poseDao.insertPoseWithTags(pose3, listOf())

            val posesWithTags1 = poseDao.filterPosesWithTags(
                tagNames = listOf()
            ).first()
            Assert.assertEquals(3, posesWithTags1.size)

            val posesWithTags2 = poseDao.filterPosesWithTags(
                tagNames = listOf("Tag2"),
                tagCount = 1
            ).first()
            Assert.assertEquals(2, posesWithTags2.size)


            val posesWithTags3 = poseDao.filterPosesWithTags(
                tagNames = listOf("Tag1", "Tag2"),
                tagCount = 2
            ).first()
            Assert.assertEquals(1, posesWithTags3.size)
        }
    }


}
