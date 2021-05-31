package com.hiberus.mobile.android.openbanktest.datasources.marvel.remote

import com.hiberus.mobile.android.openbanktest.data.marvel.source.MarvelDataStore
import com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.mapper.MarvelHeroResponseMapper
import com.hiberus.mobile.android.openbanktest.datasources.remote.API_PUBLIC_KEY
import com.hiberus.mobile.android.openbanktest.datasources.remote.errorhandling.RemoteExceptionMapper
import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHero
import io.reactivex.rxjava3.core.Single

/**
 * Remote implementation for retrieving MarvelHero instances. This class implements the
 * [MarvelRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class MarvelRemoteImpl constructor(
    private val marvelService: MarvelService,
    private val marvelResponseMapper: MarvelHeroResponseMapper,
) : MarvelDataStore {

    // region MarvelHeroes
    /**
     * Retrieve a list of [MarvelHero] instances from the [MarvelService].
     */
    override fun getMarvelHeroes(offset: Int?, pageSize: Int?): Single<List<MarvelHero>> {

        val timestamp = System.currentTimeMillis().toString()

        return marvelService.getMarvelHeroes(API_PUBLIC_KEY, timestamp, MarvelServiceFactory.getHash(timestamp), offset?:0, pageSize?:20)
            .onErrorResumeNext { throwable ->
                // If remote request fails, use remote exception mapper to transform Retrofit exception to an app exception
                Single.error(RemoteExceptionMapper.getException(throwable))
            }
            .map { it.data.results }
            .map { marvelHeroes ->
                val entities = mutableListOf<MarvelHero>()
                marvelHeroes.map { marvelHero -> entities.add(marvelResponseMapper.mapFromRemote(marvelHero)) }
                entities
            }
    }

    override fun saveMarvelHeroes(marvelHeroes: List<MarvelHero>) = throw NotImplementedError("Shouldn't be used")
    override fun clearMarvelHeroes() = throw NotImplementedError("Shouldn't be used")
    // endregion
}