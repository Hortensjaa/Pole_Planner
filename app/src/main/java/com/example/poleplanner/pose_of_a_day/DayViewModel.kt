package com.example.poleplanner.pose_of_a_day

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poleplanner.data_structure.daos.DayDao
import com.example.poleplanner.data_structure.daos.PoseDao
import com.example.poleplanner.data_structure.daos.PoseTagDao
import com.example.poleplanner.data_structure.models.Day
import com.example.poleplanner.data_structure.models.Pose
import com.example.poleplanner.data_structure.models.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class DayViewModel (
    private val poseDao: PoseDao,
    private val PTdao: PoseTagDao,
    private val dayDao: DayDao
) : ViewModel() {


    suspend fun getNewPose() {
        return withContext(Dispatchers.IO) {
            val newPose = poseDao.getRandomPose()
            val newDay = newPose?.let {
            Day(
                poseOfDayName = newPose.poseName,
                dateTime = LocalDateTime.now(),
                covered = true
            )
        }
            if (newDay != null) {
                dayDao.insertDay(newDay)
            }
        }
    }

    suspend fun getLastDay(): Day {
        return withContext(Dispatchers.IO) {
            val lastDay = dayDao.getLastDay()
            if (lastDay != null) {
                lastDay
            } else {
                Day()
            }
        }
    }

    fun uncoverLastDay() {
        viewModelScope.launch {
            val day = dayDao.getLastDay()
            if (day != null) dayDao.uncoverDay(day)
        }
    }

    suspend fun getPoseByName(name: String): Pose {
        return withContext(Dispatchers.IO) {
            poseDao.getByName(name)
        }
    }

    suspend fun getTags(poseName: String): List<Tag> {
        return withContext(Dispatchers.IO) {
            PTdao.getTagsForPose(poseName)
        }
    }
}