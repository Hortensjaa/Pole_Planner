package com.example.poleplanner.poses_list_view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poleplanner.data_structure.daos.PoseDao
import com.example.poleplanner.data_structure.models.Difficulty
import com.example.poleplanner.data_structure.models.Progress
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
import java.io.Serializable

class PosesViewModel (
    val poseDao: PoseDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _diffFilters = MutableStateFlow<Collection<Difficulty>>(Difficulty.values().toList())
    private val _progFilters = MutableStateFlow<Collection<Progress>>(Progress.values().toList())
    private val _tagNamesFilters = MutableStateFlow<Collection<String>>(emptyList())
    private var _addedOnly = MutableStateFlow(false)
    private var _savedOnly = MutableStateFlow(false)

    // wyszukiwarka
    private val _searchText = MutableStateFlow("")
    private val _isSearching = MutableStateFlow(false)

    // lista tagów
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _allTags = combine(_tagNamesFilters) {
        tags -> tags
    }.flatMapLatest { poseDao.getAllTagsFlow() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    // filtry
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _posesWithTagsList = combine(_diffFilters, _tagNamesFilters, _progFilters, _addedOnly, _savedOnly) {
        diffs, tags, prog, added, saved ->
        Quintuple(diffs, tags, prog, added, saved)
    }.flatMapLatest {
        (diffs, tags, prog, added) ->
        when {
            _savedOnly.value ->
                poseDao.filterPosesWithTagsSaved(tags, tags.size, diffs, prog, listOf(added, true))
            else ->
                poseDao.filterPosesWithTags(tags, tags.size, diffs, prog, listOf(added, true))
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    // wyszukiwarka
    private val _poses =
        _searchText
        .onEach { _isSearching.update { true } }
        .combine(_posesWithTagsList) {
                text, posesSearched ->
            if (text.isBlank()) {
                posesSearched
            } else {
                posesSearched.filter { it.pose.matchSearchText(text) }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _posesWithTagsList.value)

    private val _state = MutableStateFlow(AllPosesState())
    val state = myCombine(
        _state, _diffFilters, _tagNamesFilters, _progFilters, _poses, _searchText, _addedOnly, _savedOnly, _allTags
    ) {
        state, diffFilters, tagNamesFilters, progFilters,  poses, searchText, addedOnly, savedOnly, allTags ->
        state.copy(
            posesWithTags = poses,
            diffFilters = diffFilters,
            tagFilters = tagNamesFilters,
            progressFilters = progFilters,
            searchText = searchText,
            addedByUserOnly = addedOnly,
            savedOnly = savedOnly,
            allTags = allTags
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AllPosesState())

    fun onEvent(event: PoseEvent) {
        when(event) {

            is PoseEvent.ChangeSave -> {
                viewModelScope.launch(dispatcher) {
                    if (event.pose.saved) {
                        poseDao.unsavePose(event.pose)
                    } else {
                        poseDao.savePose(event.pose)
                    }
                }
            }

            is PoseEvent.AddDiffFilter -> {
                viewModelScope.launch(dispatcher) {
                    _diffFilters.value += event.diff
                }
            }

            is PoseEvent.DeleteDiffFilter -> {
                viewModelScope.launch(dispatcher) {
                    if (event.diff in _diffFilters.value) {
                        _diffFilters.value = _diffFilters.value.toMutableList().apply {
                            remove(event.diff)
                        }
                    }
                }
            }
            
            is PoseEvent.AddTagFilter -> {
                viewModelScope.launch(dispatcher) {
                    if (event.tag !in _tagNamesFilters.value) {
                        _tagNamesFilters.value += event.tag
                    }
                }
            }

            is PoseEvent.DeleteTagFilter -> {
                viewModelScope.launch(dispatcher) {
                    if (event.tag in _tagNamesFilters.value) {
                        _tagNamesFilters.value = _tagNamesFilters.value.toMutableList().apply {
                            remove(event.tag)
                        }
                    }
                }
            }

            is PoseEvent.ClearTagFilter -> {
                viewModelScope.launch(dispatcher) {
                    _tagNamesFilters.value = emptyList()
                }
            }

            is PoseEvent.ChangeTagFilter -> {
                viewModelScope.launch(dispatcher) {
                    if (event.tag in _tagNamesFilters.value) {
                        _tagNamesFilters.value = _tagNamesFilters.value.toMutableList().apply {
                            remove(event.tag)
                        }
                    } else {
                        _tagNamesFilters.value += event.tag
                    }
                }
            }

            is PoseEvent.AddProgressFilter -> {
                viewModelScope.launch(dispatcher) {
                    if (event.prog !in _progFilters.value) {
                        _progFilters.value += event.prog
                    }
                }
            }

            is PoseEvent.DeleteProgressFilter -> {
                viewModelScope.launch(dispatcher) {
                    if (event.prog in _progFilters.value) {
                        _progFilters.value = _progFilters.value.toMutableList().apply {
                            remove(event.prog)
                        }
                    }
                }
            }

            is PoseEvent.OnSearchTextChange -> {
                viewModelScope.launch(dispatcher) {
                    _searchText.value = event.text
                }
            }

            is PoseEvent.ClearSearcher -> {
                viewModelScope.launch(dispatcher) {
                    _searchText.value = ""
                }
            }

            is PoseEvent.ClearState -> {
                viewModelScope.launch(dispatcher) {
                    _diffFilters.value = Difficulty.values().toList()
                    _progFilters.value = Progress.values().toList()
                    _tagNamesFilters.value = emptyList()
                    _searchText.value = ""
                    _addedOnly.value = false
                    _savedOnly.value = event.savedOnly
                }
            }

            PoseEvent.ChangeAddedFilter -> {
                viewModelScope.launch(dispatcher) {
                    _addedOnly.value = !_addedOnly.value
                    Log.d("added_ony", _addedOnly.value.toString())
                }
            }
        }
    }
}

// source:
// https://stackoverflow.com/questions/65356805
@Suppress("UNCHECKED_CAST")
private fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> myCombine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    flow9: Flow<T9>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R
): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6, flow7, flow8, flow9) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
        args[6] as T7,
        args[7] as T8,
        args[8] as T9,
    )
}

// source:
// https://gist.github.com/alifhasnain/8b7109d7310b6ac64c9a13d4d80d5d33
data class Quintuple<A,B,C,D, E>(var first: A, var second: B, var third: C, var fourth: D, var fifth: E):
    Serializable {
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth)"
}
