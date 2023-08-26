package com.example.poleplanner.data_structure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _poses = _sortType
        .flatMapLatest {
            sortType ->
            when(sortType) {
                SortType.NAME -> dao.getByName()
                SortType.ID -> dao.getByID()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(AllPosesState())
    val state = combine(_state, _sortType, _poses) {
        state, sortType, poses ->
        state.copy(
            poses = poses,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AllPosesState())


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