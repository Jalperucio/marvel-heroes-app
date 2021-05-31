package com.hiberus.mobile.android.openbanktest.data.marvel.source

import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHero
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Interface defining common methods for the data operations related to MarvelHeroes.
 * This is to be extended by the remote and cache data source interfaces.
 */
interface MarvelDataStore {
    fun getMarvelHeroes(offset: Int?, pageSize: Int?): Single<List<MarvelHero>>?

    fun saveMarvelHeroes(marvelHeroes: List<MarvelHero>): Completable

    fun clearMarvelHeroes(): Completable
}