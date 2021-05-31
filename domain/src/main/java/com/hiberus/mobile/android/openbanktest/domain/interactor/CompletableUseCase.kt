package com.hiberus.mobile.android.openbanktest.domain.interactor

import com.hiberus.mobile.android.openbanktest.domain.executor.PostExecutionThread
import com.hiberus.mobile.android.openbanktest.domain.executor.ThreadExecutor
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Abstract class for a UseCase that returns an instance of a [Completable].
 */
abstract class CompletableUseCase<in Params> protected constructor(
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) {

    /**
     * Builds a [Completable] which will be used when the current [CompletableUseCase] is executed.
     */
    protected abstract fun buildUseCaseObservable(params: Params): Completable

    /**
     * Executes the current use case.
     *
     * This function is open in order to be mockeable in instrumental tests, which do not allow to mock final classes
     * or functions.
     */
    open fun execute(params: Params): Completable {
        return this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
    }
}