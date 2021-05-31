package com.hiberus.mobile.android.openbanktest.app.marvel.viewmodel

import com.hiberus.mobile.android.openbanktest.app.R
import com.hiberus.mobile.android.openbanktest.app.common.errorhandling.AppAction
import com.hiberus.mobile.android.openbanktest.app.common.errorhandling.AppError
import com.hiberus.mobile.android.openbanktest.app.common.errorhandling.ErrorBundle
import com.hiberus.mobile.android.openbanktest.app.common.errorhandling.ErrorBundleBuilder
import com.hiberus.mobile.android.openbanktest.model.exception.HTTPException
import timber.log.Timber
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * Factory used to create error bundles from an Exception as source.
 */
class MarvelErrorBundleBuilder : ErrorBundleBuilder {

    override fun build(throwable: Throwable, appAction: AppAction): ErrorBundle {
        val message = throwable.message?.let {
            if (it.isNotEmpty()) it
            else "There was an application error"
        } ?: "There was an application error"

        // Unwrap RuntimeExceptions, wrapped by emitter.tryOnError()
        val t = if (throwable is RuntimeException && throwable.cause != null && throwable.cause is Exception) throwable.cause as Exception else throwable

        val stringId = when (t) {
            is UnknownHostException -> R.string.error_no_connection
            is TimeoutException -> R.string.error_connection_timeout
            else -> when (appAction) {
                AppAction.GET_MARVEL_HEROES -> if (t is HTTPException) {
                    // TODO: Handle HTTPExceptions here
                    R.string.error_default_message
                } else {
                    R.string.error_default_message
                }
                else -> {
                    Timber.e("Action \'%s\' not supported", appAction)
                    R.string.error_default_message
                }
            }
        }

        val appError = when (t) {
            is UnknownHostException -> AppError.NO_INTERNET
            is TimeoutException -> AppError.TIMEOUT
            else -> AppError.UNKNOWN
        }

        return ErrorBundle(t, stringId, message, appAction, appError)
    }
}
