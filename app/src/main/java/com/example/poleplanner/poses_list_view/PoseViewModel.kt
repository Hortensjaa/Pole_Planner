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
     val PTdao: PoseTagDao
) : ViewModel() {

    private val _diffFilter = MutableStateFlow<Difficulty?>(null)
    private val _tagNamesFilters = MutableStateFlow<Collection<String>>(emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _poses = combine(_diffFilter, _tagNamesFilters) { difficulty, tags ->
        Pair(difficulty, tags)
    }.flatMapLatest { 
        (difficulty, tags) ->
        when {
            // todo: wieloaspektowe filtry
//            difficulty != null && tags.isNotEmpty()
//                -> poseDao.filterByDifficultyAndTags(difficulty, tags)
            tags.isNotEmpty()
                -> PTdao.getPosesWithTags(tags)
            difficulty != null
                -> poseDao.filterDifficulty(difficulty)
            else -> poseDao.sortByName()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(AllPosesState())
    val state = combine(_state, _diffFilter, _tagNamesFilters, _poses) {
        state, diffFilter, tagNamesFilters,  poses ->
        state.copy(
            poses = poses,
            diffFilter = diffFilter,
            tagFilters = tagNamesFilters
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
                    _diffFilter.value = event.diff
                }
            }

            is PoseEvent.ClearDiffFilter -> {
                viewModelScope.launch {
                    _diffFilter.value = null
                }
            }
            
            is PoseEvent.FilterByTags -> {
                viewModelScope.launch {
                    _tagNamesFilters.value = event.tags
                }
            }

            is PoseEvent.ClearTagFilter -> {
                viewModelScope.launch {
                    _tagNamesFilters.value = emptyList()
                }
            }
        }
    }
}