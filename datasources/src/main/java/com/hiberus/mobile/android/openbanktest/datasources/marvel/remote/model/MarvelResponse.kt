package com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.model

import com.google.gson.annotations.SerializedName

data class MarvelResponse(
    @SerializedName("code")
    var code: Long,
    @SerializedName("status")
    var status: String,
    @SerializedName("data")
    var data: MarvelHeroesResponse
)

data class MarvelHeroesResponse(
    @SerializedName("offset")
    var offset: Long,
    @SerializedName("limit")
    var limit: Long,
    @SerializedName("total")
    var total: Long,
    @SerializedName("count")
    var count: Long,
    @SerializedName("results")
    var results: List<MarvelHeroResponse>,
)