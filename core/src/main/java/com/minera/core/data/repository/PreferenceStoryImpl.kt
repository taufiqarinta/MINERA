package com.minera.core.data.repository

import com.minera.core.data.datastore.DataStoreSource
import com.minera.core.domain.repository.PreferenceRepository

class PreferenceStoryImpl(
    private val dataStoreSource: DataStoreSource
): PreferenceRepository {
    override suspend fun setRegistered() {
        dataStoreSource.setRegistrationFinish()
    }

    override suspend fun getRegistered(): Boolean = dataStoreSource.getRegistrationStatus().isNotEmpty()
}