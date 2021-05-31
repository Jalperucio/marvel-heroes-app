package com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Representation for a [MarvelHeroResponse] fetched from the API
 */
data class MarvelHeroResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("modified")
    val modified: String,
    @SerializedName("thumbnail")
    val thumbnail: MarvelHeroesThumbnailResponse,
    @SerializedName("resourceURI")
    val resourceURI: String,
    @SerializedName("comics")
    val comics: MarvelHeroesResourceResponse,
    @SerializedName("series")
    val series: MarvelHeroesResourceResponse,
    @SerializedName("stories")
    val stories: MarvelHeroesResourceResponse,
    @SerializedName("events")
    val events: MarvelHeroesResourceResponse,
    @SerializedName("urls")
    val urls: List<MarvelHeroesURLResponse>,
)

data class MarvelHeroesThumbnailResponse(
    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val extension: String
)

data class MarvelHeroesResourceResponse(
    @SerializedName("available")
    val available: Long,
    @SerializedName("collectionURI")
    val collectionURI: String,
    @SerializedName("items")
    val items: List<MarvelHeroesResourceItemResponse>
)

data class MarvelHeroesResourceItemResponse(
    @SerializedName("resourceURI")
    val resourceURI: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String?
)

data class MarvelHeroesURLResponse(
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)