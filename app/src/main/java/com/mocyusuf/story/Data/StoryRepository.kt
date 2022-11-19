package com.mocyusuf.story.Data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.mocyusuf.story.Local.Database.StoryDatabase
import com.mocyusuf.story.Local.Entity.Story
import com.mocyusuf.story.Remote.Network.ApiService
import com.mocyusuf.story.Remote.StoryRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(ExperimentalPagingApi::class)
class StoryRepository @Inject constructor(
    private val database: StoryDatabase,
    private val apiService: ApiService
) {

    fun getStories(auth: String) : Flow<PagingData<Story>> =
        Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = StoryRemoteMediator(
                database,
                apiService,
                generateAuthorization(auth)
            ),
            pagingSourceFactory ={
                database.storyDao().getStories()
            }
        ).flow

    private fun generateAuthorization(token: String): String {
        return "Bearer $token"
    }

}