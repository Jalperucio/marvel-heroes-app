package com.company.mobile.android.appname.app.common.log

import com.company.mobile.android.appname.app.TimberLog
import com.company.mobile.android.appname.app.common.log.helpers.CrashLibraryTree
import timber.log.Timber

object TimberLogImplementation : TimberLog {

    override fun init() {
        init("")
    }

    override fun init(userId: String) {
        Timber.plant(CrashLibraryTree(userId))
    }
}