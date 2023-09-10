package com.example.poleplanner.viewmodelsTests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.poleplanner.data_structure.daos.PoseDao
import com.example.poleplanner.data_structure.models.Pose
import com.example.poleplanner.data_structure.models.Progress
import com.example.poleplanner.data_structure.models.Tag
import com.example.poleplanner.data_structure.references.PoseWithTags
import com.example.poleplanner.pose_detail_view.DetailEvent
import com.example.poleplanner.pose_detail_view.DetailState
import com.example.poleplanner.pose_detail_view.DetailViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DetailViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val fakePoseWithTags = PoseWithTags(Pose("test"), listOf(Tag("tag1")))
    private val poseSlot = slot<Pose>()
    private val notesSlot = slot<String>()
    private val progressSlot = slot<Progress>()

    @MockK
    private lateinit var poseDao: PoseDao

    @MockK
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)

        coEvery { poseDao.getPoseWithTagsByName(any()) } returns fakePoseWithTags
        coEvery { poseDao.savePose(capture(poseSlot)) } returns Unit
        coEvery { poseDao.unsavePose(capture(poseSlot)) } returns Unit
        coEvery { poseDao.setNotes(capture(poseSlot), capture(notesSlot)) } returns Unit
        coEvery { poseDao.setProgress(capture(poseSlot), capture(progressSlot)) } returns Unit

        viewModel = DetailViewModel(poseDao, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun initialStateTest() = runTest {
        val currentState = viewModel.state.value
        assertFalse(currentState.descriptionOpen)
        assertFalse(currentState.notesOpen)
        assertFalse(currentState.notesEditing)
    }

    @Test
    fun changePoseEvent_updatesState() = runTest {
        val poseBefore = viewModel.state.value.poseWithTags.pose
        assertNotEquals(fakePoseWithTags.pose, poseBefore)
        var currentState: DetailState?

        backgroundScope.launch {
            viewModel.state.collect { state ->
                currentState = state
            }
        }
        viewModel.onEvent(DetailEvent.ChangePose(fakePoseWithTags.pose.poseName))
        advanceUntilIdle()

        currentState = viewModel.state.value
        assertNotNull(currentState)

        val poseCurrent = currentState!!.poseWithTags.pose
        assertNotNull(poseCurrent)
        assertEquals(fakePoseWithTags.pose, poseCurrent)
    }

    @Test
    fun descriptionChangeVisibilityEvent_updatesState() = runTest {
        val stateBefore = viewModel.state.value
        assertFalse(stateBefore.descriptionOpen)
        var currentState: DetailState?

        backgroundScope.launch {
            viewModel.state.collect { state ->
                currentState = state
            }
        }
        viewModel.onEvent(DetailEvent.DescriptionChangeVisibility)
        advanceUntilIdle()

        currentState = viewModel.state.value
        assertNotNull(currentState)
        assertTrue(currentState!!.descriptionOpen)
    }

    @Test
    fun notesChangeVisibilityEvent_updatesState() = runTest {
        val stateBefore = viewModel.state.value
        assertFalse(stateBefore.notesOpen)
        var currentState: DetailState?

        backgroundScope.launch {
            viewModel.state.collect { state ->
                currentState = state
            }
        }
        viewModel.onEvent(DetailEvent.NotesChangeVisibility)
        advanceUntilIdle()

        currentState = viewModel.state.value
        assertNotNull(currentState)
        assertTrue(currentState!!.notesOpen)
    }

    @Test
    fun notesEditChangeEvent_updatesState() = runTest {
        val stateBefore = viewModel.state.value
        assertFalse(stateBefore.notesEditing)
        var currentState: DetailState?

        backgroundScope.launch {
            viewModel.state.collect { state ->
                currentState = state
            }
        }
        viewModel.onEvent(DetailEvent.NotesEditChange)
        advanceUntilIdle()

        currentState = viewModel.state.value
        assertNotNull(currentState)
        assertTrue(currentState!!.notesEditing)
    }

    @Test
    fun changeSaveEvent() = runTest {
        viewModel.onEvent(DetailEvent.ChangePose(fakePoseWithTags.pose.poseName))
        advanceUntilIdle()
        viewModel.onEvent(DetailEvent.ChangeSave)
        advanceUntilIdle()
        coVerify {
            poseDao.savePose(any())
        }
        val capturedPose = poseSlot.captured
        assertEquals(fakePoseWithTags.pose, capturedPose)
    }

    @Test
    fun saveNotesEvent() = runTest {
        viewModel.onEvent(DetailEvent.ChangePose(fakePoseWithTags.pose.poseName))
        advanceUntilIdle()
        viewModel.onEvent(DetailEvent.SaveNotes("new notes"))
        advanceUntilIdle()
        coVerify {
            poseDao.setNotes(any(), any())
        }
        val capturedPose = poseSlot.captured
        val capturedNotes = notesSlot.captured
        assertEquals(fakePoseWithTags.pose, capturedPose)
        assertEquals("new notes", capturedNotes)
    }

    @Test
    fun saveProgressEvent() = runTest {
        viewModel.onEvent(DetailEvent.ChangePose(fakePoseWithTags.pose.poseName))
        advanceUntilIdle()
        viewModel.onEvent(DetailEvent.SaveProgress(Progress.PERFECT))
        advanceUntilIdle()
        coVerify {
            poseDao.setProgress(any(), any())
        }
        val capturedPose = poseSlot.captured
        val capturedProgress = progressSlot.captured
        assertEquals(fakePoseWithTags.pose, capturedPose)
        assertEquals(Progress.PERFECT, capturedProgress)
    }
}
