package com.hiberus.mobile.android.openbanktest.data.marvel.source

/**
 * Create an instance of a MarvelDataStore
 */
open class MarvelDataStoreFactory(
    private val marvelMemoryDataStore: MarvelDataStore,
    private val marvelRemoteDataStore: MarvelDataStore
) {

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveMemoryDataStore() = marvelMemoryDataStore


    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore() = marvelRemoteDataStore

}