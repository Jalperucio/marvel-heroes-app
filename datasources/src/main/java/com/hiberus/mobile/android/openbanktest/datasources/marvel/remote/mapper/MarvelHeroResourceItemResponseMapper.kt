package com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.mapper

import com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.model.MarvelHeroResponse
import com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.model.MarvelHeroesResourceItemResponse
import com.hiberus.mobile.android.openbanktest.datasources.marvel.remote.model.MarvelHeroesResourceResponse
import com.hiberus.mobile.android.openbanktest.datasources.remote.mapper.EntityMapper
import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHero
import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHeroesResource
import com.hiberus.mobile.android.openbanktest.model.marvel.MarvelHeroesResourceItem

/**
 * Map a [MarvelHeroResponse] to and from a [MarvelHero] instance when data is moving between
 * this later and the Data layer
 */
open class MarvelHeroResourceItemResponseMapper : EntityMapper<MarvelHeroesResourceItemResponse, MarvelHeroesResourceItem> {

    /**
     * Map an instance of a [MarvelHeroesResourceItemResponse] to a [MarvelHeroesResourceItem] model
     */
    override fun mapFromRemote(type: MarvelHeroesResourceItemResponse): MarvelHeroesResourceItem {
        return MarvelHeroesResourceItem(
            type.resourceURI,
            type.name,
            type.type
            )
    }
}