package com.mocyusuf.story.Remote.Model.Home

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_story")
@Parcelize
data class ListStory(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("createdAt")
    val createAt: String,

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("lat")
    val latitude: Double?,

    @field:SerializedName("lon")
    val longitude: Double?
    ) : Parcelable