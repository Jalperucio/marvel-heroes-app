package com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.mapper

import com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.model.MarvelHeroResponse
import com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.model.MarvelHeroesResourceResponse
import com.hiberus.mobile.android.openbanktest.datasources.remote.mapper.EntityMapper
import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHero
import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHeroesResource

/**
 * Map a [MarvelHeroResponse] to and from a [MarvelHero] instance when data is moving between
 * this later and the Data layer
 */
open class MarvelHeroResourceResponseMapper(
    private val resourceItemMapper: MarvelHeroResourceItemResponseMapper
) : EntityMapper<MarvelHeroesResourceResponse, MarvelHeroesResource> {


    /**
     * Map an instance of a [MarvelHeroesResourceResponse] to a [MarvelHeroesResource] model
     */
    override fun mapFromRemote(type: MarvelHeroesResourceResponse): MarvelHeroesResource {
        return MarvelHeroesResource(
            type.available,
            type.collectionURI,
            type.items.map { resourceItemMapper.mapFromRemote(it)}
            )
    }
}