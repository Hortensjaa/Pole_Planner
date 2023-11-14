package com.example.poleplanner.pose_adding_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poleplanner.data_structure.daos.PoseDao
import com.example.poleplanner.data_structure.models.Pose
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PoseAddingViewModel (
    private val poseDao: PoseDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun onEvent(event: PoseAddingEvent) {
        when(event) {

            is PoseAddingEvent.SavePose -> {
                viewModelScope.launch(dispatcher) {
                    val pose = Pose(
                        poseName = event.name,
                        description = event.description,
                        difficulty = event.diff,
                        userPhoto = event.photo,
                        addedByUser = true
                    )
                    poseDao.insertPoseWithTags(pose, emptyList())
                }
            }
        }
    }
}

