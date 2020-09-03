package com.marvelcomics.brito.data.datasource.remote.mapper

import com.marvelcomics.brito.data.datasource.remote.response.SeriesResponse
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer
import com.marvelcomics.brito.domain.entity.SeriesEntity
import com.marvelcomics.brito.infrastructure.exception.MarvelApiException
import java.util.*

class SeriesMapper(private val thumbnailMapper: ThumbnailMapper) {

    @Throws(MarvelApiException::class)
    fun transform(remoteMarvelData: RemoteMarvelContainer<SeriesResponse>): List<SeriesEntity>? {
        try {
            val seriesEntityList = ArrayList<SeriesEntity>()
            remoteMarvelData.remoteMarvelData?.results?.let {
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