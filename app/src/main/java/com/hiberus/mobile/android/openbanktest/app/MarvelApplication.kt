package com.hiberus.mobile.android.openbanktest.app

import android.app.Application
import com.hiberus.mobile.android.openbanktest.app.common.log.TimberLogImplementation
import com.hiberus.mobile.android.openbanktest.app.di.applicationModule
import com.hiberus.mobile.android.openbanktest.app.di.marvelModule
import com.hiberus.mobile.android.openbanktest.app.di.mainModule
import com.hiberus.mobile.android.openbanktest.app.di.splashModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MarvelApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin!
        startKoin {
            // Android context
            androidContext(this@MarvelApplication)
            // modules
            modules(listOf(applicationModule, splashModule, mainModule, marvelModule))
        }

        // Initialize logging library
        TimberLogImplementation.init()
    }
}
