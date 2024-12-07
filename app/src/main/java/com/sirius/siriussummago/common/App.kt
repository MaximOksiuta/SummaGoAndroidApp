package com.sirius.siriussummago.common

import android.app.Application
import com.sirius.siriussummago.di.appModule
import com.sirius.siriussummago.di.dataModule
import com.sirius.siriussummago.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    companion object{
        lateinit var instance: Application
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    dataModule,
                    domainModule
                )
            )
        }
    }
}