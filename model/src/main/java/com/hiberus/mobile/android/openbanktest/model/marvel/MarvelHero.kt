package com.hiberus.mobile.android.openbanktest.model.marvel

/**
 * Representation for a [MarvelHero] fetched from an external layer data source
 */
data class MarvelHero(
    val id: Long,
    val name: String,
    val description: String,
    //val modified: String,
    val thumbnail: String,
    val resourceURI: String,
    val comics: MarvelHeroesResource,
    val series: MarvelHeroesResource,
    val stories: MarvelHeroesResource,
    val events: MarvelHeroesResource,
    val urlDetail: String?,
    val urlWiki: String?,
    val urlComicLink: String?
)

data class MarvelHeroesResource(
    val available: Long,
    val collectionURI: String,
    val items: List<MarvelHeroesResourceItem>
)

data class MarvelHeroesResourceItem(
    val resourceURI: String,
    val name: String,
    val type: String?
)