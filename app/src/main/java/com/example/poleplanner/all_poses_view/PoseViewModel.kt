package com.example.poleplanner.all_poses_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poleplanner.data_structure.Difficulty
import com.example.poleplanner.data_structure.PoseDao
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

    private val _filter = MutableStateFlow<Difficulty?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _poses = _filter
        .flatMapLatest {
            filter ->
            when(filter) {
                Difficulty.BEGGINER -> dao.filterDifficulty(Difficulty.BEGGINER)
                Difficulty.INTERMEDIATE -> dao.filterDifficulty(Difficulty.INTERMEDIATE)
                Difficulty.ADVANCED -> dao.filterDifficulty(Difficulty.ADVANCED)
                else -> dao.sortByName()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(AllPosesState())
    val state = combine(_state, _filter, _poses) {
        state, filter, poses ->
        state.copy(
            poses = poses,
            diffFilter = filter
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AllPosesState())


    fun onEvent(event: PoseEvent) {
        when(event) {

            is PoseEvent.SavePose -> {
                viewModelScope.launch {
                    dao.savePose(event.pose)
                }
            }
            
            is PoseEvent.FilterByDiff -> {
                viewModelScope.launch {
                    _filter.value = event.diff
                }
            }

            is PoseEvent.ClearDiffFilter -> {
                viewModelScope.launch {
                    _filter.value = null
                }
            }
        }
    }
}