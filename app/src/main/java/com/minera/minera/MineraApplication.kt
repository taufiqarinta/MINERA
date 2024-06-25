package com.minera.minera

import android.app.Application
import com.minera.core.di.firebaseModule
import com.minera.core.di.preferencesModule
import com.minera.core.di.repositoryModule
import com.minera.minera.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MineraApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MineraApplication)
            modules(listOf(
                firebaseModule,
                repositoryModule,
                viewModelModule,
                preferencesModule
            ))
        }
    }
}