package com.minera.core.data.datastore

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class DataStoreSource(private val dataStore: PreferencesDataStore) {

    suspend fun setRegistrationFinish() = dataStore.setString(REGISTRATION_STATUS, "finish")
    suspend fun getRegistrationStatus() = dataStore.getString(REGISTRATION_STATUS).first()

    companion object {
        val REGISTRATION_STATUS = stringPreferencesKey("registration_status")
    }
}