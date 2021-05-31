package com.hiberus.mobile.android.openbanktest.app

// Timber implementation for debug and release build variants adapted from:
// https://medium.com/@caueferreira/timber-enhancing-your-logging-experience-330e8af97341
// https://github.com/caueferreira/timber-example
interface TimberLog {

    fun init()

    fun init(userId: String)
}
