package com.mocyusuf.story.Utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mocyusuf.story.Data.DataRepository
import com.mocyusuf.story.ViewModel.HomeViewModel
import com.mocyusuf.story.ViewModel.LoginRegisterViewModel
import com.mocyusuf.story.ViewModel.UploadStoryViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val dataRepository: DataRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginRegisterViewModel::class.java) -> {
                LoginRegisterViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(UploadStoryViewModel::class.java) -> {
                UploadStoryViewModel(dataRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Class ViewModel not Implement")
            }
        }
    }

}