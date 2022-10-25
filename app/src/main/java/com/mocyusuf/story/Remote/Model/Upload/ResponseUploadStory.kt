package com.mocyusuf.story.Remote.Model.Upload

import com.google.gson.annotations.SerializedName

data class ResponseUploadStory(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
