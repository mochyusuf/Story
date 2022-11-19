package com.mocyusuf.story.ViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import androidx.recyclerview.widget.ListUpdateCallback
import com.mocyusuf.story.Adapter.StoryAdapter
import com.mocyusuf.story.Data.DataRepository
import com.mocyusuf.story.Data.StoryRepository
import com.mocyusuf.story.Local.Entity.Story
import com.mocyusuf.story.Utils.*
import junit.framework.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @Mock
    private lateinit var storyRepository: StoryRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var homeViewModel : HomeViewModel

    private val dummyToken = DataDummy.generateDummyToken()

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(storyRepository)
    }

    @Test
    fun `Get stories successfully`() = runTest {
        val dummyStories = DataDummy.generateDummyListStory()

        val data: PagingData<Story> = StoryPagingSource.snapshot(dummyStories)
        val expectedQuote = flowOf(data)
        `when`(storyRepository.getStories(dummyToken)).thenReturn(expectedQuote)

        val actualQuote: PagingData<Story> = homeViewModel.getStories(dummyToken).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DiffCallback,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = coroutinesTestRule.testDispatcher,
            workerDispatcher = coroutinesTestRule.testDispatcher
        )
        differ.submitData(actualQuote)

        advanceUntilIdle()

        verify(storyRepository).getStories(dummyToken)
        assertNotNull(differ.snapshot())
        assertEquals(dummyStories,differ.snapshot())
        assertTrue(dummyStories.size > 0)
    }

    @Test
    fun `Get stories Failed`() = runTest {
        val dummyStories = DataDummy.generateDummyListStoryFailed()

        val data: PagingData<Story> = StoryPagingSource.snapshot(dummyStories)
        val expectedQuote = flowOf(data)
        `when`(storyRepository.getStories(dummyToken)).thenReturn(expectedQuote)

        val actualQuote: PagingData<Story> = homeViewModel.getStories(dummyToken).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DiffCallback,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = coroutinesTestRule.testDispatcher,
            workerDispatcher = coroutinesTestRule.testDispatcher
        )
        differ.submitData(actualQuote)

        advanceUntilIdle()

        verify(storyRepository).getStories(dummyToken)
        assertNotNull(differ.snapshot())
        assertEquals(dummyStories,differ.snapshot())
        assertTrue(dummyStories.size == 0)
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}