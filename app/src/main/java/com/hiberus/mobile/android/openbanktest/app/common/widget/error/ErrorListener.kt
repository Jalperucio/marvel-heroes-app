package com.hiberus.mobile.android.openbanktest.app.common.widget.error

import com.hiberus.mobile.android.openbanktest.app.common.errorhandling.ErrorBundle

interface ErrorListener {

    fun onRetry(errorBundle: ErrorBundle)
}