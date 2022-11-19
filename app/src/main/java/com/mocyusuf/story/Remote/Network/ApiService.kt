package com.mocyusuf.story.Remote.Network

import com.mocyusuf.story.Remote.Model.Home.ResponseHome
import com.mocyusuf.story.Remote.Model.Login.ResponseLogin
import com.mocyusuf.story.Remote.Model.Register.ResponseRegister
import com.mocyusuf.story.Remote.Model.Upload.ResponseUploadStory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import retrofit2.Response

interface ApiService {

    @POST("register")
    @FormUrlEncoded
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<ResponseRegister>

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<ResponseLogin>

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") auth: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int? = null
    ) : Response<ResponseHome>

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Header("Authorization") auth: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody?,
        @Part("lon") long: RequestBody?
    ) : Response<ResponseUploadStory>


}