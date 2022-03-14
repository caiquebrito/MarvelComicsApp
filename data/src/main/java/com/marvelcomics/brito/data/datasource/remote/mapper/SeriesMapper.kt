package com.marvelcomics.brito.data.datasource.remote.mapper

import com.marvelcomics.brito.data.datasource.remote.response.SeriesResponse
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainerResponse
import com.marvelcomics.brito.domain.entity.SeriesEntity
import com.marvelcomics.brito.infrastructure.exception.MarvelApiException

class SeriesMapper(private val thumbnailMapper: ThumbnailMapper) {

    @Throws(MarvelApiException::class)
    fun transform(remoteMarvelDataResponse: RemoteMarvelContainerResponse<SeriesResponse>): List<SeriesEntity>? {
        try {
            val seriesEntityList = ArrayList<SeriesEntity>()
            remoteMarvelDataResponse.remoteMarvelDataResponse?.results?.let {
                for (seriesResponse in it) {
                    val seriesEntity = SeriesEntity(
                        seriesResponse.id,
                        seriesResponse.title,
                        seriesResponse.description,
                        thumbnailMapper.transform(seriesResponse.thumbnailResponse)
                    )
                    seriesEntityList.add(seriesEntity)
                }
                return seriesEntityList
            } ?: let {
                throw MarvelApiException("Result from server return nulls")
            }
        } catch (e: NullPointerException) {
            throw MarvelApiException("Result from server return nulls", e)
        }
    }
}
