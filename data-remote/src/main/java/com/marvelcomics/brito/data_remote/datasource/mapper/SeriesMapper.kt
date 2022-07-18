package com.marvelcomics.brito.data_remote.datasource.mapper

import com.marvelcomics.brito.data_remote.datasource.response.SeriesResponse
import com.marvelcomics.brito.data_remote.datasource.response.model.RemoteMarvelContainerResponse
import com.marvelcomics.brito.data_remote.exception.MarvelApiException
import com.marvelcomics.brito.domain.models.SeriesDomain

class SeriesMapper(private val thumbnailMapper: ThumbnailMapper) :
    RemoteMapper<RemoteMarvelContainerResponse<SeriesResponse>, List<SeriesDomain>> {

    @Throws(MarvelApiException::class)
    override fun transform(input: RemoteMarvelContainerResponse<SeriesResponse>): List<SeriesDomain> {
        try {
            val output = ArrayList<SeriesDomain>()
            input.remoteMarvelDataResponse?.results?.let {
                for (seriesResponse in it) {
                    val seriesEntity = SeriesDomain(
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
