package com.hiberus.mobile.android.openbanktest.domain.interactor

import com.hiberus.mobile.android.openbanktest.domain.executor.PostExecutionThread
import com.hiberus.mobile.android.openbanktest.domain.executor.ThreadExecutor
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Abstract class for a UseCase that returns an instance of a [Single].
 */
abstract class SingleUseCase<T, in Params> constructor(
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) {

    /**
     * Builds a [Single] which will be used when the current [SingleUseCase] is executed.
     */
    protected abstract fun buildUseCaseObservable(params: Params? = null): Single<T>

    /**
     * Executes the current use case.
     *
     * This function is open in order to be mockeable in instrumental tests, which do not allow to mock final classes
     * or functions.
     */
    open fun execute(params: Params? = null): Single<T> {
        return this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
    }
}