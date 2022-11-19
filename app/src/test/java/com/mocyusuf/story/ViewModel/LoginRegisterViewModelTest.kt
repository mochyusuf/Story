package com.mocyusuf.story.ViewModel

import com.mocyusuf.story.Data.DataRepository
import com.mocyusuf.story.Remote.Model.Login.ResponseLogin
import com.mocyusuf.story.Remote.Model.Register.ResponseRegister
import com.mocyusuf.story.Utils.CoroutinesTestRule
import com.mocyusuf.story.Utils.DataDummy
import com.mocyusuf.story.Utils.NetworkResult
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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
@RunWith(MockitoJUnitRunner::class)
class LoginRegisterViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var dataRepository: DataRepository
    private lateinit var loginRegisterViewModel: LoginRegisterViewModel

    private val dummyLoginResponse = DataDummy.generateDummyLoginResponse()
    private val dummyRegisterResponse = DataDummy.generateDummyRegisterResponse()
    private val dummyName = "Yusuf"
    private val dummyEmail = "mochyusuf100@gmail.com"
    private val dummyPassword = "123456"

    @Before
    fun setup() {
        loginRegisterViewModel = LoginRegisterViewModel(dataRepository)
    }

    @Test
    fun `Login is successfully - NetworkResult Success`(): Unit = runTest {
        val expectedResponse = flowOf(NetworkResult.Success(dummyLoginResponse))

        `when`(dataRepository.login(dummyEmail, dummyPassword)).thenReturn(expectedResponse)

        loginRegisterViewModel.login(dummyEmail, dummyPassword).collect { result ->
            when(result) {
                is NetworkResult.Success -> {
                    assertNotNull(result.data)
                    assertSame(result.data, dummyLoginResponse)
                }
            }
        }
        verify(dataRepository).login(dummyEmail, dummyPassword)
    }

    @Test
    fun `Login is Failed - NetworkResult Failed`(): Unit = runTest {
        val expectedResponse : Flow<NetworkResult<ResponseLogin>> = flowOf(NetworkResult.Error("failed"))

        `when`(dataRepository.login(dummyEmail, dummyPassword)).thenReturn(expectedResponse)

        loginRegisterViewModel.login(dummyEmail, dummyPassword).collect { result ->
            when(result) {
                is NetworkResult.Error -> {
                    assertNull(result.data)
                }
            }
        }
        verify(dataRepository).login(dummyEmail, dummyPassword)
    }

    @Test
    fun `Register is successfully - NetworkResult Success`(): Unit = runTest {
        val expectedResponse = flowOf(NetworkResult.Success(dummyRegisterResponse))

        `when`(dataRepository.register(dummyName, dummyEmail, dummyPassword)).thenReturn(expectedResponse)

        loginRegisterViewModel.register(dummyName, dummyEmail, dummyPassword).collect { result ->
            when(result) {
                is NetworkResult.Success -> {
                    assertNotNull(result.data)
                    assertSame(result.data, dummyRegisterResponse)
                }
            }
        }
        verify(dataRepository).register(dummyName, dummyEmail, dummyPassword)
    }

    @Test
    fun `Register is Failed - NetworkResult Failed`(): Unit = runTest {
        val expectedResponse : Flow<NetworkResult<ResponseRegister>> = flowOf(NetworkResult.Error("failed"))

        `when`(dataRepository.register(dummyName,dummyEmail, dummyPassword)).thenReturn(expectedResponse)

        loginRegisterViewModel.register(dummyName, dummyEmail, dummyPassword).collect { result ->
            when(result) {
                is NetworkResult.Error -> {
                    assertNull(result.data)
                }
            }
        }
        verify(dataRepository).register(dummyName,dummyEmail, dummyPassword)
    }

}