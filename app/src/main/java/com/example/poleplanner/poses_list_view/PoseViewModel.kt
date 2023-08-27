package com.example.poleplanner.poses_list_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poleplanner.data_structure.Difficulty
import com.example.poleplanner.data_structure.PoseDao
import com.example.poleplanner.data_structure.PoseTagDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PoseViewModel (
     private val poseDao: PoseDao,
     val dao: PoseTagDao
) : ViewModel() {

    private val _filter = MutableStateFlow<Difficulty?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _poses = _filter
        .flatMapLatest {
            filter ->
            when(filter) {
                Difficulty.BEGGINER -> poseDao.filterDifficulty(Difficulty.BEGGINER)
                Difficulty.INTERMEDIATE -> poseDao.filterDifficulty(Difficulty.INTERMEDIATE)
                Difficulty.ADVANCED -> poseDao.filterDifficulty(Difficulty.ADVANCED)
                else -> poseDao.sortByName()
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

            is PoseEvent.ChangeSave -> {
                viewModelScope.launch {
                    if (event.pose.saved) {
                        poseDao.unsavePose(event.pose)
                    } else {
                        poseDao.savePose(event.pose)
                    }
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