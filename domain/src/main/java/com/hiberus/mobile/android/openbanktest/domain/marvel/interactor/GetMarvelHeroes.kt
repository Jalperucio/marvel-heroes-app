package com.hiberus.mobile.android.openbanktest.domain.marvel.interactor

import com.hiberus.mobile.android.openbanktest.domain.executor.PostExecutionThread
import com.hiberus.mobile.android.openbanktest.domain.executor.ThreadExecutor
import com.hiberus.mobile.android.openbanktest.domain.interactor.SingleUseCase
import com.hiberus.mobile.android.openbanktest.domain.marvel.repository.MarvelRepository
import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHero
import io.reactivex.rxjava3.core.Single

open class GetMarvelHeroes(
    private val marvelRepository: MarvelRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<MarvelHero>, GetMarvelHeroes.GetMarvelHeroesParams?>(threadExecutor, postExecutionThread) {

    data class GetMarvelHeroesParams(val forceRemote: Boolean = false, val offset: Int, val pageSize: Int)

    override fun buildUseCaseObservable(params: GetMarvelHeroesParams?): Single<List<MarvelHero>> {
        return params?.let { it ->
            marvelRepository.getMarvelHeroes(it.forceRemote, it.offset, it.pageSize)
        } ?: throw RuntimeException("Mandatory parameters GetMarvelHeroesParams")
    }
}