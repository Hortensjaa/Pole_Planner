package com.example.poleplanner.pose_of_a_day

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poleplanner.data_structure.models.Pose
import com.example.poleplanner.data_structure.daos.PoseDao
import com.example.poleplanner.data_structure.daos.PoseTagDao
import com.example.poleplanner.data_structure.models.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class DayViewModel (
    val poseDao: PoseDao,
    private val PTdao: PoseTagDao
) : ViewModel() {

    private val _state = MutableStateFlow(DayState())
    private val _pose = MutableStateFlow<Pose?>(null)
    private val _lastDraw = MutableStateFlow<LocalDateTime>(LocalDateTime.MIN)
    private val _covered = MutableStateFlow(true)

    val state = combine(_state, _pose, _lastDraw, _covered) { state, pose, lastDraw, covered ->
        if (pose != null) {
            state.copy(
                pose = pose,
                lastDrawDate = lastDraw,
                covered = covered
            )
        } else {
            state
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DayState())

    suspend fun getNewPose() : Pose? {
        return withContext(Dispatchers.IO) {
            poseDao.getRandomPose()
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


    fun onEvent(event: DayEvent) {
        when(event) {

            is DayEvent.UncoverPose -> {
                _state.update { it.copy(covered = false) }
                _state.update { it.copy(lastDrawDate = LocalDateTime.now()) }
            }

            is DayEvent.CoverPose -> {
                _state.update { it.copy(covered = true) }
            }

            is DayEvent.GetNewPose -> {
                viewModelScope.launch {
                    val newPose = poseDao.getRandomPose()
                    _state.update { it.copy(pose = newPose!!) }
                }
            }

            is DayEvent.ChangePose -> {
                viewModelScope.launch {
                    _state.update { it.copy(pose = event.pose) }
                }
            }
        }
    }
}