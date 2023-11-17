package com.example.poleplanner.pose_detail_view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poleplanner.data_structure.daos.PoseDao
import com.example.poleplanner.data_structure.references.PoseWithTags
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel (
    private val poseDao: PoseDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    private val _poseWithTags = MutableStateFlow<PoseWithTags?>(null)

    val state = combine(_state, _poseWithTags) { state, pose ->
        if (pose != null) {
            state.copy(
                poseWithTags = pose
            )
        } else {
            state
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DetailState())

    fun onEvent(event: DetailEvent) {
        when(event) {

            is DetailEvent.ChangeSave -> {
                viewModelScope.launch(dispatcher) {
                    if (_state.value.poseWithTags.pose.saved) {
                        poseDao.unsavePose(_state.value.poseWithTags.pose)
                    } else {
                        poseDao.savePose(_state.value.poseWithTags.pose)
                    }
                }
            }

            is DetailEvent.DescriptionChangeVisibility -> {
                viewModelScope.launch(dispatcher) {
                    _state.update { it.copy(descriptionOpen = !it.descriptionOpen) }
                }

            }

            is DetailEvent.NotesChangeVisibility -> {
                viewModelScope.launch(dispatcher) {
                    _state.update { it.copy(notesOpen = !it.notesOpen) }
                }
            }

            is DetailEvent.ChangePose -> {
                CoroutineScope(dispatcher).launch {
                    try {
                        val poseWithTags = poseDao.getPoseWithTagsByName(event.poseName)
                        _state.update { it.copy(poseWithTags = poseWithTags) }
                    } catch (e: Exception) {
                        Log.d("pose_with_no_tags", e.message.toString())
                        try {
                            val pose = poseDao.getByName(event.poseName)
                            _state.update { it.copy(poseWithTags = PoseWithTags(pose, listOf())) }
                        } catch (e: Exception) {
                            Log.d("no_pose_found", e.message.toString())
                        }
                    }
                }
            }

            DetailEvent.DescriptionEditChange ->
                viewModelScope.launch(dispatcher) {
                    _state.update { it.copy(descriptionOpen = true) }
                    _state.update { it.copy(descriptionEditing = !it.descriptionEditing) }
            }

            is DetailEvent.NotesEditChange -> {
                viewModelScope.launch(dispatcher) {
                    _state.update { it.copy(notesOpen = true) }
                    _state.update { it.copy(notesEditing = !it.notesEditing) }
                }
            }

            is DetailEvent.SaveDescription -> {
                viewModelScope.launch(dispatcher) {
                    poseDao.setDescription(_state.value.poseWithTags.pose, event.description)
                }
            }

            is DetailEvent.SaveNotes -> {
                viewModelScope.launch(dispatcher) {
                    poseDao.setNotes(_state.value.poseWithTags.pose, event.notes)
                }
            }

            is DetailEvent.SaveProgress -> {
                viewModelScope.launch(dispatcher) {
                    poseDao.setProgress(_state.value.poseWithTags.pose, event.progress)
                }
            }

            DetailEvent.DeletePose -> {
                viewModelScope.launch(dispatcher) {
                    poseDao.deleteByName(_state.value.poseWithTags.pose.poseName)
                }
            }
        }
    }
}