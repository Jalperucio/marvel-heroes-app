package com.hiberus.mobile.android.openbanktest.datasources.remote.errorhandling

/**
 * [Exception] throw by the remote package when a there is a network connection returns a [Throwable].
 */
class ThrowableWrapperException : Exception {

    constructor() : super()

    constructor(cause: Throwable) : super(cause)
}
