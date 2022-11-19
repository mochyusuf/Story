package com.mocyusuf.story.Data

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.ListUpdateCallback
import com.mocyusuf.story.Adapter.StoryAdapter
import com.mocyusuf.story.Local.Database.StoryDatabase
import com.mocyusuf.story.Remote.Network.ApiService
import com.mocyusuf.story.Utils.CoroutinesTestRule
import com.mocyusuf.story.Utils.DataDummy
import com.mocyusuf.story.Utils.PagedTestDataSource
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    private val dummyToken = DataDummy.generateDummyToken()
    private val dummyStoriesResponse = DataDummy.generateDummyStoriesResponse()

    @Test
    fun `Get stories with pager - successfully`() = runTest {
        val dummyStories = DataDummy.generateDummyListStory()
        val data = PagedTestDataSource.snapshot(dummyStories)

        val expectedResult = flowOf(data)

        Mockito.`when`(storyRepository.getStories(dummyToken)).thenReturn(expectedResult)

        storyRepository.getStories(dummyToken).collect { result ->
            val differ = AsyncPagingDataDiffer(
                diffCallback = StoryAdapter.DiffCallback,
                updateCallback = noopListUpdateCallback,
                mainDispatcher = coroutinesTestRule.testDispatcher,
                workerDispatcher = coroutinesTestRule.testDispatcher
            )
            differ.submitData(result)
            assertNotNull(differ.snapshot())
            assertEquals(
                dummyStoriesResponse.listStory.size,
                differ.snapshot().size
            )
        }
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}