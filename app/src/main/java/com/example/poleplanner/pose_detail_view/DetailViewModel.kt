package com.example.poleplanner.pose_detail_view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poleplanner.data_structure.daos.PoseDao
import com.example.poleplanner.data_structure.references.PoseWithTags
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel (
    private val poseDao: PoseDao
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
                viewModelScope.launch {
                    if (_state.value.poseWithTags.pose.saved) {
                        poseDao.unsavePose(_state.value.poseWithTags.pose)
                    } else {
                        poseDao.savePose(_state.value.poseWithTags.pose)
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
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val poseWithTags = poseDao.getPoseWithTagsByName(event.poseName)
                        _state.update { it.copy(poseWithTags = poseWithTags) }
                    } catch (e: Exception) {
                        Log.d("change_pose", e.message.toString())
                    }
                }
            }

            is DetailEvent.SaveNotes -> {
                viewModelScope.launch {
                    poseDao.setNotes(_state.value.poseWithTags.pose, event.notes)
                }
            }

            is DetailEvent.SaveProgress -> {
                viewModelScope.launch {
                    poseDao.setProgress(_state.value.poseWithTags.pose, event.progress)
                }
            }
        }
    }
}