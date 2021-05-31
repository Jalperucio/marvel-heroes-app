package com.company.mobile.android.appname.app.common.log.helpers

import android.text.TextUtils
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashlyticsLogging : CrashLibrary {

    override fun log(priority: Int, tag: String?, message: String) {
        tag?.let {
            val prefix = when(priority) {
                Log.ASSERT -> "A"
                Log.VERBOSE -> "V"
                Log.DEBUG -> "D"
                Log.INFO -> "I"
                Log.WARN -> "W"
                Log.ERROR -> "E"
                else -> "U" // Unknown
            }
            FirebaseCrashlytics.getInstance().log("$prefix/$it: $message")
        } ?: FirebaseCrashlytics.getInstance().log(message)
    }

    override fun logException(throwable: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }

    override fun setUser(userId: String) {
        if (!TextUtils.isEmpty(userId)) {
            FirebaseCrashlytics.getInstance().setUserId(userId)
        }
    }
}
