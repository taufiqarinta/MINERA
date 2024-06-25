package com.minera.core.domain.repository

import com.minera.core.data.utils.Resource
import com.minera.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signIn(email: String, password: String): Flow<Resource<String>>
    fun signUp(user: User, password: String): Flow<Resource<String>>
    fun getRegistrationStatus(): Flow<Resource<Int>>
    fun signOut()
    fun isUserExist(): Flow<Boolean>
}