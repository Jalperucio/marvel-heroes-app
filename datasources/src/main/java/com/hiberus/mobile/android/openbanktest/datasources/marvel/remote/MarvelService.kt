package com.hiberus.mobile.android.openbanktest.datasources.marvel.remote

import com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.model.MarvelResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Defines the abstract methods used for interacting with the Marvel API
 */
interface MarvelService {

    @GET("v1/public/characters")
    fun getMarvelHeroes(
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<MarvelResponse>
}