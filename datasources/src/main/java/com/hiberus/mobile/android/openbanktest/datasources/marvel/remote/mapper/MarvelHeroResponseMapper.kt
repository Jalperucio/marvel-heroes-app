package com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.mapper

import com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.model.MarvelHeroResponse
import com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.model.MarvelHeroesURLResponse
import com.hiberus.mobile.android.openbanktest.datasources.remote.mapper.EntityMapper
import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHero

/**
 * Map a [MarvelHeroResponse] to and from a [MarvelHero] instance when data is moving between
 * this later and the Data layer
 */
open class MarvelHeroResponseMapper(
    private val resourceMapper: MarvelHeroResourceResponseMapper
) : EntityMapper<MarvelHeroResponse, MarvelHero> {

    /**
     * Map an instance of a [MarvelHeroResponse] to a [MarvelHero] model
     */
    override fun mapFromRemote(type: MarvelHeroResponse): MarvelHero {
        return MarvelHero(
            type.id,
            type.name,
            type.description,
            //type.modified,
            type.thumbnail.path + "." + type.thumbnail.extension,
            type.resourceURI,
            resourceMapper.mapFromRemote(type.comics),
            resourceMapper.mapFromRemote(type.series),
            resourceMapper.mapFromRemote(type.stories),
            resourceMapper.mapFromRemote(type.events),
            type.urls.firstOrNull{ it.type == "detail" }?.url,
            type.urls.firstOrNull{it.type == "wiki"}?.url,
            type.urls.firstOrNull{it.type == "comiclink"}?.url
        )
    }
}