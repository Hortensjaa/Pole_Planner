package com.example.poleplanner.pose_adding_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poleplanner.data_structure.daos.PoseDao
import com.example.poleplanner.data_structure.models.Pose
import com.example.poleplanner.data_structure.models.Tag
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PoseAddingViewModel (
    private val poseDao: PoseDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _selectedTags = MutableStateFlow<List<Tag>>(emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _allTags = combine(_selectedTags) {
            tags -> tags
    }.flatMapLatest { poseDao.getAllTagsFlow() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(PoseAddingState())

    val state = combine(_state, _allTags, _selectedTags) {
        state, allTags, selectedTags ->
        state.copy(
            allTags = allTags,
            selectedTags = selectedTags
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PoseAddingState())

    fun onEvent(event: PoseAddingEvent) {
        when(event) {

            is PoseAddingEvent.SavePose -> {
                viewModelScope.launch(dispatcher) {
                    val pose = Pose(
                        poseName = event.name,
                        poseDescription = event.description,
                        difficulty = event.diff,
                        userPhoto = event.photo,
                        addedByUser = true
                    )
                    poseDao.insertPoseWithTags(pose, _selectedTags.value)
                    _selectedTags.value = emptyList()
                }
            }

            is PoseAddingEvent.AddSelectedTag -> {
                viewModelScope.launch(dispatcher) {
                    if (event.tag !in _selectedTags.value) {
                        _selectedTags.value += event.tag
                    }
                }
            }

            is PoseAddingEvent.DeleteSelectedTag -> {
                viewModelScope.launch(dispatcher) {
                    if (event.tag in _selectedTags.value) {
                        _selectedTags.value = _selectedTags.value.toMutableList().apply {
                            remove(event.tag)
                        }
                    }
                }
            }

            is PoseAddingEvent.UpdateTags -> {
                viewModelScope.launch(dispatcher) {
                    if (event.tag in _selectedTags.value) {
                        _selectedTags.value = _selectedTags.value.toMutableList().apply {
                            remove(event.tag)
                        }
                    } else {
                        _selectedTags.value += event.tag
                    }
                }
            }

            is PoseAddingEvent.ClearTags -> {
                viewModelScope.launch(dispatcher) {
                    _selectedTags.value = emptyList()
                }
            }
        }
    }
}

