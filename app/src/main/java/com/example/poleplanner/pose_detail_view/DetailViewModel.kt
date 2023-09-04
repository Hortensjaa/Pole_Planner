package com.example.poleplanner.pose_detail_view

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

class DetailViewModel (
    private val poseDao: PoseDao,
    private val PTdao: PoseTagDao
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    private val _pose = MutableStateFlow<Pose?>(null)

    val state = combine(_state, _pose) { state, pose ->
        if (pose != null) {
            state.copy(
                pose = pose
            )
        } else {
            state
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DetailState())

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

    fun onEvent(event: DetailEvent) {
        when(event) {

            is DetailEvent.ChangeSave -> {
                viewModelScope.launch {
                    if (_state.value.pose.saved) {
                        poseDao.unsavePose(_state.value.pose)
                    } else {
                        poseDao.savePose(_state.value.pose)
                    }
                }
            }

            is DetailEvent.DescriptionChangeVisibility -> {
                _state.update { it.copy(descriptionOpen = !it.descriptionOpen) }
            }

            is DetailEvent.NotesChangeVisibility -> {
                _state.update { it.copy(notesOpen = !it.notesOpen) }
            }

            is DetailEvent.NotesEditChange -> {
                _state.update { it.copy(notesOpen = true) }
                _state.update { it.copy(notesEditing = !it.notesEditing) }
            }

            is DetailEvent.ChangePose -> {
                viewModelScope.launch {
                    _state.update { it.copy(pose = event.pose) }
                }
            }

            is DetailEvent.SaveNotes -> {
                viewModelScope.launch {
                    poseDao.setNotes(_state.value.pose, event.notes)
                }
            }

            is DetailEvent.SaveProgress -> {
                viewModelScope.launch {
                    poseDao.setProgress(_state.value.pose, event.progress)
                }
            }

        }
    }
}