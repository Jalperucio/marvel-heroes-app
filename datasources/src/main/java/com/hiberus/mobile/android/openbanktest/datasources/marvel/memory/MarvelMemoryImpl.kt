package com.hiberus.mobile.android.openbanktest.datasources.marvel.memory

import com.hiberus.mobile.android.openbanktest.data.marvel.source.MarvelDataStore
import com.hiberus.mobile.android.openbanktest.datasources.memory.MarvelMemory
import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHero

/**
 * Remote implementation for retrieving MarvelHero instances. This class implements the
 * [MarvelRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class MarvelMemoryImpl constructor(
    private val memory: MarvelMemory
) : MarvelDataStore {

    /**
     * Retrieve a list of [MarvelHeroes] instances from the [MarvelService].
     */
    override fun getMarvelHeroes(offset: Int?, pageSize: Int?) = memory.getMarvelHeroes()

    override fun saveMarvelHeroes(marvelHeroes: List<MarvelHero>) = memory.saveMarvelHeroes(marvelHeroes)

    override fun clearMarvelHeroes() = memory.clearMemory()

}