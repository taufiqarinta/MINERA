package com.minera.core.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.minera.core.data.datastore.DataStoreSource
import com.minera.core.data.datastore.PreferencesDataStore
import com.minera.core.data.repository.AuthRepositoryImpl
import com.minera.core.data.repository.PreferenceStoryImpl
import com.minera.core.data.source.AuthDataSource
import com.minera.core.domain.repository.AuthRepository
import com.minera.core.domain.repository.PreferenceRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val firebaseModule = module {
    single { Firebase.firestore }
    single { FirebaseAuth.getInstance() }
}

val repositoryModule = module {
    single { AuthDataSource(get(), get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }

    single { DataStoreSource(get()) }
    single<PreferenceRepository> { PreferenceStoryImpl(get()) }
}

val preferencesModule = module {
    single { PreferencesDataStore(androidContext()) }
}