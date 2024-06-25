package com.minera.core.domain.repository

interface PreferenceRepository {
    suspend fun setRegistered()
    suspend fun getRegistered(): Boolean
}