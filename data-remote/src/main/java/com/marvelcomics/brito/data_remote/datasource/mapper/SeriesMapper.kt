package com.marvelcomics.brito.data_remote.datasource.mapper

import com.marvelcomics.brito.data_remote.datasource.response.SeriesResponse
import com.marvelcomics.brito.data_remote.datasource.response.model.RemoteMarvelContainerResponse
import com.marvelcomics.brito.data_remote.exception.MarvelApiException
import com.marvelcomics.brito.entity.SeriesEntity

class SeriesMapper(private val thumbnailMapper: ThumbnailMapper) :
    RemoteMapper<RemoteMarvelContainerResponse<SeriesResponse>, List<SeriesEntity>> {

    @Throws(MarvelApiException::class)
    override fun transform(input: RemoteMarvelContainerResponse<SeriesResponse>): List<SeriesEntity> {
        try {
            val output = ArrayList<SeriesEntity>()
            input.remoteMarvelDataResponse?.results?.let {
                for (seriesResponse in it) {
                    val seriesEntity = SeriesEntity(
                        seriesResponse.id,
                        seriesResponse.title,
                        seriesResponse.description,
                        thumbnailMapper.transform(seriesResponse.thumbnailResponse)
                    )
                    output.add(seriesEntity)
                }
                return output
            } ?: let {
                throw MarvelApiException("Result from server return nulls")
            }
        } catch (e: NullPointerException) {
            throw MarvelApiException("Result from server return nulls", e)
        }
    }
}
