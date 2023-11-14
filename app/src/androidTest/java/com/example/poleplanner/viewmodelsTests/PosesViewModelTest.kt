package com.example.poleplanner.viewmodelsTests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.poleplanner.data_structure.daos.PoseDao
import com.example.poleplanner.data_structure.models.Difficulty
import com.example.poleplanner.data_structure.models.Pose
import com.example.poleplanner.data_structure.models.Progress
import com.example.poleplanner.data_structure.models.Tag
import com.example.poleplanner.data_structure.references.PoseWithTags
import com.example.poleplanner.poses_list_view.AllPosesState
import com.example.poleplanner.poses_list_view.PoseEvent
import com.example.poleplanner.poses_list_view.PosesViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class PosesViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val fakeTags = flowOf(listOf(Tag("tag1"), Tag("tag2")))
    private val fakePosesWithTags = flowOf(listOf(
        PoseWithTags(Pose("pose1"), listOf(Tag("tag1"), Tag("tag2"))),
        PoseWithTags(Pose("pose2"), listOf(Tag("tag2"))),
        PoseWithTags(Pose("pose3", saved = true), listOf(Tag("tag2"))),
    ))

    @MockK
    private lateinit var poseDao: PoseDao

    @MockK
    private lateinit var viewModel: PosesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)

        coEvery { poseDao.getAllTagsFlow() } returns fakeTags
        coEvery { poseDao.filterPosesWithTags(any(), any(), any(), any())
            } returns fakePosesWithTags
        coEvery { poseDao.filterPosesWithTagsSaved(any(), any(), any(), any())
            } returns fakePosesWithTags.filter { it.first().pose.saved }
        viewModel = PosesViewModel(poseDao, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun initialStateTest() = runTest {
        val initialState = viewModel.state.value
        Assert.assertFalse(initialState.savedOnly)

        Assert.assertTrue(initialState.allTags.isEmpty())
        Assert.assertTrue(initialState.posesWithTags.isEmpty())
        Assert.assertTrue(initialState.tagFilters.isEmpty())

        Assert.assertEquals(Difficulty.values().toList(), initialState.diffFilters)
        Assert.assertEquals(Progress.values().toList(), initialState.progressFilters)
        Assert.assertEquals("", initialState.searchText)
    }

    @Test
    fun diffFiltersEvents_updatesState() = runTest {
        val diffToChange = Difficulty.BEGINNER
        val stateBefore = viewModel.state.value
        Assert.assertEquals(Difficulty.values().toList(), stateBefore.diffFilters)
        var currentState: AllPosesState?

        backgroundScope.launch {
            viewModel.state.collect { state ->
                currentState = state
            }
        }
        viewModel.onEvent(PoseEvent.DeleteDiffFilter(diffToChange))
        advanceUntilIdle()

        currentState = viewModel.state.value
        Assert.assertNotNull(currentState)
        Assert.assertFalse(diffToChange in currentState!!.diffFilters)
        Assert.assertEquals(stateBefore.diffFilters.size-1, currentState!!.diffFilters.size)

        viewModel.onEvent(PoseEvent.AddDiffFilter(diffToChange))
        advanceUntilIdle()
        currentState = viewModel.state.value
        Assert.assertNotNull(currentState)
        Assert.assertTrue(diffToChange in currentState!!.diffFilters)
        Assert.assertEquals(
            stateBefore.diffFilters.size,
            currentState!!.diffFilters.size)
    }

    @Test
    fun progressFiltersEvents_updatesState() = runTest {
        val progToChange = Progress.PERFECT
        val stateBefore = viewModel.state.value
        Assert.assertEquals(Progress.values().toList(), stateBefore.progressFilters)
        var currentState: AllPosesState?

        backgroundScope.launch {
            viewModel.state.collect { state ->
                currentState = state
            }
        }
        viewModel.onEvent(PoseEvent.DeleteProgressFilter(progToChange))
        advanceUntilIdle()

        currentState = viewModel.state.value
        Assert.assertNotNull(currentState)
        Assert.assertFalse(progToChange in currentState!!.progressFilters)
        Assert.assertEquals(
            stateBefore.progressFilters.size-1,
            currentState!!.progressFilters.size)

        viewModel.onEvent(PoseEvent.AddProgressFilter(progToChange))
        advanceUntilIdle()
        currentState = viewModel.state.value
        Assert.assertNotNull(currentState)
        Assert.assertTrue(progToChange in currentState!!.progressFilters)
        Assert.assertEquals(
            stateBefore.progressFilters.size,
            currentState!!.progressFilters.size)
    }

    @Test
    fun tagsFiltersEvents_updatesState() = runTest {
        val tag1 = Tag("tag1")
        val tag2 = Tag("tag2")
        val stateBefore = viewModel.state.value
        Assert.assertTrue(stateBefore.tagFilters.isEmpty())
        var currentState: AllPosesState?

        backgroundScope.launch {
            viewModel.state.collect { state ->
                currentState = state
            }
        }

        viewModel.onEvent(PoseEvent.AddTagFilter(tag1.tagName))
        viewModel.onEvent(PoseEvent.AddTagFilter(tag2.tagName))
        viewModel.onEvent(PoseEvent.AddTagFilter(tag2.tagName))
        advanceUntilIdle()

        currentState = viewModel.state.value
        Assert.assertNotNull(currentState)
        Assert.assertTrue(tag1.tagName in currentState!!.tagFilters)
        Assert.assertTrue(tag2.tagName in currentState!!.tagFilters)
        Assert.assertEquals(2, currentState!!.tagFilters.size)

        viewModel.onEvent(PoseEvent.DeleteTagFilter(tag2.tagName))
        advanceUntilIdle()

        currentState = viewModel.state.value
        Assert.assertNotNull(currentState)
        Assert.assertFalse(tag2.tagName in currentState!!.tagFilters)
        Assert.assertEquals(1, currentState!!.tagFilters.size)


        viewModel.onEvent(PoseEvent.ClearTagFilter)
        advanceUntilIdle()

        currentState = viewModel.state.value
        Assert.assertNotNull(currentState)
        Assert.assertEquals(0, currentState!!.tagFilters.size)

        viewModel.onEvent(PoseEvent.ChangeTagFilter(tag1.tagName))
        advanceUntilIdle()
        currentState = viewModel.state.value
        Assert.assertEquals(1, currentState!!.tagFilters.size)
        viewModel.onEvent(PoseEvent.ChangeTagFilter(tag1.tagName))
        advanceUntilIdle()
        currentState = viewModel.state.value
        Assert.assertEquals(0, currentState!!.tagFilters.size)
    }

    @Test
    fun searchingEvents_updatesState() = runTest {
        val searchedText = "nazwa"
        val stateBefore = viewModel.state.value
        Assert.assertEquals("", stateBefore.searchText)
        var currentState: AllPosesState?

        backgroundScope.launch {
            viewModel.state.collect { state ->
                currentState = state
            }
        }
        viewModel.onEvent(PoseEvent.OnSearchTextChange(searchedText))
        advanceUntilIdle()

        currentState = viewModel.state.value
        Assert.assertNotNull(currentState)
        Assert.assertEquals(searchedText, currentState!!.searchText)

        viewModel.onEvent(PoseEvent.ClearSearcher)
        advanceUntilIdle()

        currentState = viewModel.state.value
        Assert.assertNotNull(currentState)
        Assert.assertEquals("", currentState!!.searchText)
    }

    @Test
    fun clearStateEvent_updatesState() = runTest {
        var currentState: AllPosesState?

        backgroundScope.launch {
            viewModel.state.collect { state ->
                currentState = state
            }
        }

        viewModel.onEvent(PoseEvent.DeleteDiffFilter(Difficulty.BEGINNER))
        viewModel.onEvent(PoseEvent.DeleteProgressFilter(Progress.PERFECT))
        viewModel.onEvent(PoseEvent.AddTagFilter("some tag"))
        viewModel.onEvent(PoseEvent.OnSearchTextChange("some name"))
        advanceUntilIdle()

        viewModel.onEvent(PoseEvent.ClearState(true))
        advanceUntilIdle()

        currentState = viewModel.state.value
        Assert.assertNotNull(currentState)
        Assert.assertTrue(currentState!!.savedOnly)
        Assert.assertTrue(currentState!!.tagFilters.isEmpty())
        Assert.assertEquals(Difficulty.values().toList(), currentState!!.diffFilters)
        Assert.assertEquals(Progress.values().toList(), currentState!!.progressFilters)
        Assert.assertEquals("", currentState!!.searchText)
    }
}