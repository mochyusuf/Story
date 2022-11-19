package com.mocyusuf.story.Local.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mocyusuf.story.Local.Entity.Story

@Database(
    entities =[Story::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}