package com.minera.minera.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.minera.core.data.utils.Resource
import com.minera.core.domain.model.User
import com.minera.core.domain.repository.AuthRepository

class SignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    fun signUp(user: User, password: String): LiveData<Resource<String>> =
        authRepository.signUp(user, password).asLiveData()
}