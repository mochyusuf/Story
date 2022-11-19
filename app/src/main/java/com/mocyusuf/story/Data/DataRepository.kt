package com.mocyusuf.story.Data

import com.mocyusuf.story.Local.Entity.Story
import com.mocyusuf.story.Remote.Model.Home.ResponseHome
import com.mocyusuf.story.Remote.Model.Login.ResponseLogin
import com.mocyusuf.story.Remote.Model.Register.ResponseRegister
import com.mocyusuf.story.Remote.Model.Upload.ResponseUploadStory
import com.mocyusuf.story.Remote.Network.ApiConfig
import com.mocyusuf.story.Utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val dataSource: DataSource
) : ApiConfig() {

    suspend fun getStoriesLocation(auth: String): Flow<NetworkResult<ResponseHome>> =
        flow {
            emit(safeApiCall {
                dataSource.getStoriesLocation(generateAuthorization(auth))
            })
        }.flowOn(Dispatchers.IO)

    suspend fun uploadStory(
        auth: String,
        description: String,
        lat: String?,
        lon: String?,
        file: MultipartBody.Part
    ): Flow<NetworkResult<ResponseUploadStory>> =
        flow {
            emit(safeApiCall {
                val generateToken = generateAuthorization(auth)
                dataSource.uploadStory(generateToken, description, lat, lon, file)
            })
        }.flowOn(Dispatchers.IO)

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Flow<NetworkResult<ResponseRegister>> = flow {
        emit(safeApiCall {
            dataSource.register(name, email, password)
        })
    }.flowOn(Dispatchers.IO)

    suspend fun login(email: String, password: String): Flow<NetworkResult<ResponseLogin>> =
        flow {
            emit(safeApiCall {
                dataSource.login(email, password)
            })
        }.flowOn(Dispatchers.IO)


    private fun generateAuthorization(token: String): String {
        return "Bearer $token"
    }

}