package com.olesix.mynotes

import android.app.Application
import com.olesix.mynotes.di.editViewModule
import com.olesix.mynotes.di.mainViewModule
import com.olesix.mynotes.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinApp)
            modules(mainViewModule, editViewModule, repositoryModule)
        }
    }
}
