package com.minera.core.data.repository

import com.minera.core.data.source.AuthDataSource
import com.minera.core.data.utils.FirebaseResponse
import com.minera.core.data.utils.Resource
import com.minera.core.domain.model.User
import com.minera.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource
): AuthRepository {
    override fun signIn(email: String, password: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        when (val firebaseResponse = authDataSource.signIn(email, password).first()) {
            is FirebaseResponse.Error -> {
                emit(Resource.Error(firebaseResponse.errorMessage))
            }
            is FirebaseResponse.Success -> {
                emit(Resource.Success(firebaseResponse.data))
            }
            else -> {}
        }
    }

    override fun signUp(user: User, password: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        when (val firebaseResponse = authDataSource.signUp(user, password).first()) {
            is FirebaseResponse.Error -> {
                emit(Resource.Error(firebaseResponse.errorMessage))
            }
            is FirebaseResponse.Success -> {
                emit(Resource.Success(firebaseResponse.data))
            }
            else -> {}
        }
    }

    override fun getRegistrationStatus(): Flow<Resource<Int>> = flow {
        emit(Resource.Loading())
        when (val firebaseResponse = authDataSource.getRegistrationStatus().first()) {
            is FirebaseResponse.Error -> {
                emit(Resource.Error(firebaseResponse.errorMessage))
            }
            is FirebaseResponse.Success -> {
                emit(Resource.Success(firebaseResponse.data))
            }
            else -> {
                emit(Resource.Error("Data tidak ditemukan"))
            }
        }
    }

    override fun signOut() {
        authDataSource.signOut()
    }

    override fun isUserExist(): Flow<Boolean> = flow {
        if (authDataSource.isUserExist().first()) emit(true)
        else emit(false)
    }
}