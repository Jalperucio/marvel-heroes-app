package com.hiberus.mobile.android.openbanktest.data.marvel.repository

import com.hiberus.mobile.android.openbanktest.data.marvel.source.MarvelDataStoreFactory
import com.hiberus.mobile.android.openbanktest.domain.marvel.repository.MarvelRepository
import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHero
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Provides an implementation of the [MarvelRepository] interface for communicating to and from
 * data sources
 */
class MarvelDataRepository(private val factory: MarvelDataStoreFactory) : MarvelRepository {

    // region MarvelHeroes
    override fun getMarvelHeroes(forceRemote: Boolean, offset: Int, pageSize: Int): Single<List<MarvelHero>> {
        return if (forceRemote && offset == 0) {
            factory.retrieveMemoryDataStore().clearMarvelHeroes().andThen(
                fetchRemoteMarvelHeroes(0, pageSize)
            )
        } else if (forceRemote && offset != 0) {
            fetchRemoteMarvelHeroes(offset, pageSize)
        } else
            factory.retrieveMemoryDataStore().getMarvelHeroes(null, null)
                ?: run { fetchRemoteMarvelHeroes(offset, pageSize) }
    }

    private fun fetchRemoteMarvelHeroes(offset: Int, pageSize: Int): Single<List<MarvelHero>> {
        return factory.retrieveRemoteDataStore().getMarvelHeroes(offset, pageSize)?.let { single ->
            single.flatMap { marvelHeroesList ->
                factory.retrieveMemoryDataStore().saveMarvelHeroes(marvelHeroesList).andThen(
                    single
                )
            }
        } ?: throw RuntimeException("Couldn't recover remote heroes data")
    }

    override fun saveMarvelHeroes(marvelHeroes: List<MarvelHero>): Completable {
        return factory.retrieveMemoryDataStore().saveMarvelHeroes(marvelHeroes)
    }

    override fun clearMarvelHeroes(): Completable {
        return factory.retrieveMemoryDataStore().clearMarvelHeroes()
    }
    // endregion
}