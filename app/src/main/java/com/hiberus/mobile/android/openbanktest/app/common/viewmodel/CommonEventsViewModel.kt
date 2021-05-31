package com.hiberus.mobile.android.openbanktest.app.common.viewmodel

import androidx.lifecycle.ViewModel
import com.hiberus.mobile.android.openbanktest.app.common.viewmodel.CommonEvent.Unauthorized
import com.hiberus.mobile.android.openbanktest.model.exception.HTTPException
import io.reactivex.rxjava3.observers.DisposableCompletableObserver
import io.reactivex.rxjava3.observers.DisposableSingleObserver

abstract class CommonEventsViewModel : ViewModel() {

    abstract class CompletableRemoteInterceptor() : DisposableCompletableObserver() {

        abstract override fun onComplete()

        override fun onError(e: Throwable) { onRegularError(e) }

        abstract fun onRegularError(e: Throwable)
    }

    abstract class SingleRemoteInterceptor<R>() : DisposableSingleObserver<R>() {

        abstract override fun onSuccess(t: R)

        override fun onError(e: Throwable) { onRegularError(e) }

        abstract fun onRegularError(e: Throwable)
    }
}