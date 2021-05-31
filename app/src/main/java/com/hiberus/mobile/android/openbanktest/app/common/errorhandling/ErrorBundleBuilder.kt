package com.hiberus.mobile.android.openbanktest.app.common.errorhandling

interface ErrorBundleBuilder {

    fun build(throwable: Throwable, appAction: AppAction): ErrorBundle
}
