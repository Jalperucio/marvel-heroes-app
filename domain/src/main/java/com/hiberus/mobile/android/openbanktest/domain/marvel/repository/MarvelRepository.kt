package com.hiberus.mobile.android.openbanktest.domain.marvel.repository

import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHero
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface MarvelRepository {

    fun getMarvelHeroes(forceRemote: Boolean = false, offset: Int, pageSize: Int): Single<List<MarvelHero>>

    fun saveMarvelHeroes(marvelHeroes: List<MarvelHero>): Completable

    fun clearMarvelHeroes(): Completable

}