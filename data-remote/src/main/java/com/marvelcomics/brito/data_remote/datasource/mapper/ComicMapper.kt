package com.marvelcomics.brito.data_remote.datasource.mapper

import com.marvelcomics.brito.data_remote.datasource.response.ComicResponse
import com.marvelcomics.brito.data_remote.datasource.response.model.RemoteMarvelContainerResponse
import com.marvelcomics.brito.data_remote.exception.MarvelApiException
import com.marvelcomics.brito.domain.models.ComicDomain

class ComicMapper(private val thumbnailMapper: ThumbnailMapper) {

    @Throws(MarvelApiException::class)
    fun transform(remoteMarvelDataResponse: RemoteMarvelContainerResponse<ComicResponse>): List<ComicDomain> {
        try {
            val comicEntityList = ArrayList<ComicDomain>()
            remoteMarvelDataResponse.remoteMarvelDataResponse?.results?.let {
                for (comicResponse in it) {
                    val comicEntity = ComicDomain(
                        comicResponse.id,
                        comicResponse.title,
                        comicResponse.description,
                        thumbnailMapper.transform(comicResponse.thumbnailResponse)
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
