package com.minera.minera.presentation.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.minera.core.data.utils.Resource
import com.minera.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignInViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    fun signIn(email: String, password: String): LiveData<Resource<String>> =
        authRepository.signIn(email, password).asLiveData()

    fun getRegistrationStatus(): Flow<Resource<Int>> =
        authRepository.getRegistrationStatus()

    fun signOut() = authRepository.signOut()

    fun isUserExist(): LiveData<Boolean> = authRepository.isUserExist().asLiveData()
}