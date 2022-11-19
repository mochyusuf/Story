package com.mocyusuf.story.ViewModel

import androidx.paging.ExperimentalPagingApi
import com.mocyusuf.story.Data.DataRepository
import com.mocyusuf.story.Remote.Model.Home.ResponseHome
import com.mocyusuf.story.Utils.DataDummy
import com.mocyusuf.story.Utils.NetworkResult
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {

    @Mock
    private lateinit var dataRepository: DataRepository
    private lateinit var mapsViewModel: MapsViewModel

    private val dummyStoriesResponse = DataDummy.generateDummyStoriesResponse()
    private val dummyToken = DataDummy.generateDummyToken()

    @Before
    fun setup() {
        mapsViewModel = MapsViewModel(dataRepository)
    }

    @Test
    fun `Get location story is successfully - NetworkResult Success`(): Unit = runTest {
        val expectedResponse = flowOf(NetworkResult.Success(dummyStoriesResponse))

        `when`(dataRepository.getStoriesLocation(dummyToken)).thenReturn(expectedResponse)

        mapsViewModel.getStoriesLocation(dummyToken).collect { result ->
            assertFalse(result.data!!.error)
            assertNotNull(result.data)
            assertSame(result.data, dummyStoriesResponse)
        }

        verify(dataRepository).getStoriesLocation(dummyToken)
    }

    @Test
    fun `Get location story is failed - NetworkResult Error`(): Unit = runTest {
        val expectedResponse : Flow<NetworkResult<ResponseHome>> = flowOf(NetworkResult.Error("failed"))

        `when`(dataRepository.getStoriesLocation(dummyToken)).thenReturn(expectedResponse)

        mapsViewModel.getStoriesLocation(dummyToken).collect { result ->
            assertNull(result.data)
            assertNotNull(result.message)
        }

        verify(dataRepository).getStoriesLocation(dummyToken)
    }


}