package com.marvelcomics.brito.data.datasource.remote.mapper

import com.marvelcomics.brito.data.datasource.remote.response.ComicResponse
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer
import com.marvelcomics.brito.domain.entity.ComicEntity
import com.marvelcomics.brito.infrastructure.exception.MarvelApiException
import java.util.*

class ComicMapper(private val thumbnailMapper: ThumbnailMapper?) {

    @Throws(MarvelApiException::class)
    fun transform(remoteMarvelData: RemoteMarvelContainer<ComicResponse>): List<ComicEntity>? {
        try {
            val comicEntityList = ArrayList<ComicEntity>()
            remoteMarvelData.remoteMarvelData?.results?.let {
                for (comicResponse in it) {
                    val comicEntity = ComicEntity(
                        comicResponse.id,
                        comicResponse.title,
                        comicResponse.description,
                        thumbnailMapper!!.transform(comicResponse.thumbnailResponse)
                    )
                    comicEntityList.add(comicEntity)
                }
                return comicEntityList
            } ?: let {
                throw MarvelApiException("Result from server return nulls")
            }
        } catch (e: NullPointerException) {
            throw MarvelApiException("Result from server return nulls", e)
        }
    }
}