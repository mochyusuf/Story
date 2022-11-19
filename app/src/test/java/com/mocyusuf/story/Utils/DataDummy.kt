package com.mocyusuf.story.Utils

import com.mocyusuf.story.Local.Entity.Story
import com.mocyusuf.story.Remote.Model.Home.ListStory
import com.mocyusuf.story.Remote.Model.Home.ResponseHome
import com.mocyusuf.story.Remote.Model.Login.ResponseLogin
import com.mocyusuf.story.Remote.Model.Register.ResponseRegister
import com.mocyusuf.story.Remote.Model.Upload.ResponseUploadStory
import com.mocyusuf.story.Remote.Model.Login.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

object DataDummy {
    fun generateDummyStoriesResponse(): ResponseHome {
        val error = false
        val message = "Stories fetched successfully"
        val listStory = mutableListOf<ListStory>()

        for (i in 0 until 10) {
            val story = ListStory(
                id = "story-FvU4u0Vp2S3PMsFg",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                createAt = "2022-29-10T06:34:18.598Z",
                name = "MochYusuf",
                description = "Lorem Ipsum",
                longitude = -16.002,
                latitude = -10.212
            )

            listStory.add(story)
        }

        return ResponseHome(error, message, listStory)
    }

    fun generateDummyListStory(): List<Story> {
        val items = arrayListOf<Story>()

        for (i in 0 until 10) {
            val story = Story(
                id = "story-FvU4u0Vp2S3PMsFg",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                createdAt = "2022-01-08T06:34:18.598Z",
                name = "MochYusuf",
                description = "Lorem Ipsum",
                lon = -16.002,
                lat = -10.212
            )

            items.add(story)
        }

        return items
    }

    fun generateDummyListStoryFailed(): List<Story> {
        return arrayListOf<Story>()
    }

    fun generateDummyLoginResponse(): ResponseLogin {
        val loginResult = Result (
            userId = "story-Mufi8a889bui",
            name = "Yusuf",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXNHamQzZWx0Wk1zckl1M3IiLCJpYXQiOjE2NTcyMTc2NjV9.ZlZaTNeZX3Db4KYwTkIaiUTBy5oX-3DkSmlSnpSuZws"
        )

        return ResponseLogin(
            result = loginResult,
            error = false,
            message = "success"
        )
    }

    fun generateDummyRegisterResponse(): ResponseRegister {
        return ResponseRegister(
            error = false,
            message = "success"
        )
    }

    fun generateDummyMultipartFile(): MultipartBody.Part {
        val dummyText = "text"
        return MultipartBody.Part.create(dummyText.toRequestBody())
    }

    fun generateDummyRequestBody(): String {
        return "text"
    }

    fun generateDummyFileUploadResponse(): ResponseUploadStory {
        return ResponseUploadStory(
            error = false,
            message = "success"
        )
    }

    fun generateDummyToken() : String {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXNHamQzZWx0Wk1zckl1M3IiLCJpYXQiOjE2NTcyMTc2NjV9.ZlZaTNeZX3Db4KYwTkIaiUTBy5oX-3DkSmlSnpSuZws"
    }
}