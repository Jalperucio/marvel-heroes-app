package com.company.mobile.android.appname.app.common.log.helpers

import android.util.Log
import timber.log.Timber

class CrashLibraryTree(userId: String) : Timber.Tree() {

    private val crashLibrary = CrashlyticsLogging()

    init {
        this.crashLibrary.setUser(userId)
    }

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        this.crashLibrary.log(priority, tag, message)

        throwable?.let {
            this.crashLibrary.logException(it)
        }
    }
}
