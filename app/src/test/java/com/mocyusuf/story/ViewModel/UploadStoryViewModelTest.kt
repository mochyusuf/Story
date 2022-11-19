package com.mocyusuf.story.ViewModel

import androidx.paging.ExperimentalPagingApi
import com.mocyusuf.story.Data.DataRepository
import com.mocyusuf.story.Remote.Model.Upload.ResponseUploadStory
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
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UploadStoryViewModelTest {

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var uploadStoryViewModel: UploadStoryViewModel

    private val dummyToken = DataDummy.generateDummyToken()
    private val dummyUploadResponse = DataDummy.generateDummyFileUploadResponse()
    private val dummyMultipart = DataDummy.generateDummyMultipartFile()
    private val dummyDescription = DataDummy.generateDummyRequestBody()

    @Before
    fun setUp() {
        uploadStoryViewModel = UploadStoryViewModel(dataRepository)
    }

    @Test
    fun `Upload file successfully`() = runTest {
        val expectedResponse = flowOf(NetworkResult.Success(dummyUploadResponse))

        Mockito.`when`(
            dataRepository.uploadStory(
                dummyToken,
                dummyDescription,
                "",
                "",
                dummyMultipart
            )
        ).thenReturn(expectedResponse)

        uploadStoryViewModel.uploadStory(dummyToken, dummyDescription, "", "", dummyMultipart)
            .collect { result ->
                assertNotNull(result.data)
                assertSame(dummyUploadResponse, result.data)
                assertFalse(result.data!!.error)
            }

        verify(dataRepository).uploadStory(dummyToken, dummyDescription, "", "", dummyMultipart)
    }

    @Test
    fun `Upload file Failed`() = runTest {
        val expectedResponse : Flow<NetworkResult<ResponseUploadStory>> = flowOf(NetworkResult.Error("failed"))

        Mockito.`when`(
            dataRepository.uploadStory(
                dummyToken,
                dummyDescription,
                "",
                "",
                dummyMultipart
            )
        ).thenReturn(expectedResponse)

        uploadStoryViewModel.uploadStory(dummyToken, dummyDescription, "", "", dummyMultipart)
            .collect { result ->
                when(result){
                    is NetworkResult.Success -> {
                        assertFalse(result.data!!.error)
                        assertNotSame(dummyUploadResponse, result.data)
                    }
                    is NetworkResult.Error -> {
                        assertNotNull(result.message)
                    }
                    is NetworkResult.Loading ->{}
                }
            }

        verify(dataRepository).uploadStory(dummyToken, dummyDescription, "", "", dummyMultipart)
    }
}