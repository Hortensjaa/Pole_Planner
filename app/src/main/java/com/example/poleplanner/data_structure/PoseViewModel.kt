package com.example.poleplanner.data_structure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PoseViewModel (
    private val dao: PoseDao
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.NAME)
    private val _poses = _sortType
        .flatMapLatest {
            sortType ->
            when(sortType) {
                SortType.NAME -> dao.getPosesByName()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), InitialData.poses)
    private val _state = MutableStateFlow(AppState())
    val state = combine(_state, _sortType, _poses) {
        state, sortType, poses ->
        state.copy(
            poses = poses,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppState())

    fun onEvent(event: PoseEvent) {
        when(event) {
            is PoseEvent.SavePose -> {
                viewModelScope.launch {
                    dao.savePose(event.pose)
                }
            }

            is PoseEvent.SortPoses -> {
                viewModelScope.launch {
                    _sortType.value = event.sortType
                }
            }
        }
    }
}