package com.example.poleplanner.pose_detail_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poleplanner.data_structure.Pose
import com.example.poleplanner.data_structure.PoseDao
import com.example.poleplanner.data_structure.PoseTagDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class DetailViewModel (
    private val poseDao: PoseDao,
    val PTdao: PoseTagDao
) : ViewModel() {

    private val _state = MutableStateFlow(PoseDetailState())
    private val _pose = MutableStateFlow<Pose?>(null)

    val state = combine(_state, _pose) { state, pose ->
        if (pose != null) {
            state.copy(
                pose = pose
            )
        } else {
            state
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PoseDetailState())

    suspend fun getPoseByName(name: String): Pose {
        return withContext(Dispatchers.IO) {
            poseDao.getByName(name)
        }
    }

    fun onEvent(event: DetailEvent) {
        when(event) {

            is DetailEvent.changeDescription -> {
                _state.update { it.copy(descriptionOpen = !it.descriptionOpen) }
            }

            is DetailEvent.changeNotes -> {
                _state.update { it.copy(notesOpen = !it.notesOpen) }
            }
//            is DetailEvent.ChangePose -> {
//                Log.d("kdmdslds", "1: ${event.poseName}")
//                viewModelScope.launch {
//                    Log.d("kdmdslds", "2: ${event.poseName}")
//                    try {
//                        _pose.value =
//                            poseDao.getByName(event.poseName)
//                    } catch (e: Exception) {
//                        Log.d("kdmdslds", "aa: ${e.message}")
//                    }
//
//                    Log.d("kdmdslds", "3")
//                }
//            }

        }
    }
}