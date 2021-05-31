package com.hiberus.mobile.android.openbanktest.datasources.memory

import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHero
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class MarvelMemory {

    private var marvelheroes: MutableList<MarvelHero>? = null

    fun getMarvelHeroes(): Single<List<MarvelHero>>? = marvelheroes?.let { Single.just(it as List<MarvelHero>) }

    fun saveMarvelHeroes(heroes: List<MarvelHero>?): Completable {
        marvelheroes?.addAll(heroes as MutableList) ?: run { marvelheroes = heroes as MutableList<MarvelHero>}
        return Completable.complete()
    }

    fun clearMemory(): Completable {
        marvelheroes = null
        return Completable.complete()
    }
}