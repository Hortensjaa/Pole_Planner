package com.example.poleplanner.poses_list_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poleplanner.data_structure.Difficulty
import com.example.poleplanner.data_structure.PoseDao
import com.example.poleplanner.data_structure.PoseTagDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PosesViewModel (
    val poseDao: PoseDao,
    val PTdao: PoseTagDao
) : ViewModel() {

    private val _diffFilters = MutableStateFlow<Collection<Difficulty>>(Difficulty.values().toList())
    private val _tagNamesFilters = MutableStateFlow<Collection<String>>(emptyList())
    private var _savedOnly = MutableStateFlow(false)

    // wyszukiwarka
    private val _searchText = MutableStateFlow("")
    private val _isSearching = MutableStateFlow(false)

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    // filtry
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _posesList = combine(_diffFilters, _tagNamesFilters, _savedOnly) {
        diffs, tags, saved ->
        Triple(diffs, tags, saved)
    }.flatMapLatest { 
        (diffs, tags) ->
        when {
            _savedOnly.value -> PTdao.filterSaved(tags, tags.size, diffs)
            else -> PTdao.filterDifficultyAndTags(tags, tags.size, diffs)
        }

        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    // wyszukiwarka
    private val _poses = _searchText
        .onEach { _isSearching.update { true } }
        .combine(_posesList) {
                text, posesSearched ->
            if (text.isBlank()) {
                posesSearched
            } else {
                posesSearched.filter { it.matchSearchText(text) }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _posesList.value)

    private val _state = MutableStateFlow(AllPosesState())
    val state = myCombine(_state, _diffFilters, _tagNamesFilters, _poses, _searchText, _savedOnly) {
        state, diffFilters, tagNamesFilters,  poses, searchText, savedOnly ->
        state.copy(
            poses = poses,
            diffFilters = diffFilters,
            tagFilters = tagNamesFilters,
            searchText = searchText,
            savedOnly = savedOnly
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

            is PoseEvent.AddDiffFilter -> {
                viewModelScope.launch {
                    _diffFilters.value += event.diff
                }
            }

            is PoseEvent.DeleteDiffFilter -> {
                viewModelScope.launch {
                    if (event.diff in _diffFilters.value) {
                        _diffFilters.value = _diffFilters.value.toMutableList().apply {
                            remove(event.diff)
                        }
                    }
                }
            }
            
            is PoseEvent.AddTagFilter -> {
                viewModelScope.launch {
                    if (event.tag !in _tagNamesFilters.value) {
                        _tagNamesFilters.value += event.tag
                    }
                }
            }

            is PoseEvent.DeleteTagFilter -> {
                viewModelScope.launch {
                    if (event.tag in _tagNamesFilters.value) {
                        _tagNamesFilters.value = _tagNamesFilters.value.toMutableList().apply {
                            remove(event.tag)
                        }
                    }
                }
            }

            is PoseEvent.ClearTagFilter -> {
                viewModelScope.launch {
                    _tagNamesFilters.value = emptyList()
                }
            }

            is PoseEvent.ClearSearcher -> {
                viewModelScope.launch {
                    _searchText.value = ""
                }
            }

            is PoseEvent.ClearState -> {
                viewModelScope.launch {
                    _diffFilters.value = Difficulty.values().toList()
                    _tagNamesFilters.value = emptyList()
                    _searchText.value = ""
                    _savedOnly.value = event.savedOnly
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
private fun <T1, T2, T3, T4, T5, T6, R> myCombine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    transform: suspend (T1, T2, T3, T4, T5, T6) -> R
): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6
    )
}
