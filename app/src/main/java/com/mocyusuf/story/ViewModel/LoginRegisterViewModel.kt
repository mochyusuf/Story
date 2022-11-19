package com.mocyusuf.story.ViewModel

import androidx.lifecycle.ViewModel
import com.mocyusuf.story.Data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginRegisterViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {

    suspend fun register(name: String, email: String, password: String) = dataRepository.register(name, email, password)

    suspend fun login(email: String, password: String) = dataRepository.login(email, password)

}