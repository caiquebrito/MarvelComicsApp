package com.marvelcomics.brito.data.ktor

import com.marvelcomics.brito.data.datasource.remote.mapper.ComicMapper
import com.marvelcomics.brito.data.ktor.model.RemoteMarvelContainerK
import com.marvelcomics.brito.data.webservice.MarvelWebService
import com.marvelcomics.brito.domain.entity.ComicEntity
import com.marvelcomics.brito.domain.ktor.IComicKRepository
import com.marvelcomics.brito.infrastructure.exception.MarvelMapperException
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class ComicKRepository(
    private val httpClient: HttpClient,
    private val webService: MarvelWebService,
    private val comicMapper: ComicMapper
) : IComicKRepository {
    override suspend fun getComics(characterId: Int): List<ComicEntity> {

        val url = "characters/$characterId/comics"

        val returningObject = httpClient.get<RemoteMarvelContainerK<ComicResponseK>>(url)
        println(returningObject)

        return comicMapper.transform(webService.comics(characterId))
            ?: throw MarvelMapperException("Error mapping character", null)
    }
}
