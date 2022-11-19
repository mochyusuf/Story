package com.mocyusuf.story.ViewModel

import androidx.lifecycle.ViewModel
import com.mocyusuf.story.Data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {
    suspend fun getStoriesLocation(auth: String) = dataRepository.getStoriesLocation(auth)
}