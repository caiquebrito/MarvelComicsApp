package com.marvelcomics.brito.data.repository.series

import com.marvelcomics.brito.data.datasource.remote.response.SeriesResponse
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.data.handler.ResponseHandler
import com.marvelcomics.brito.data.webservice.MarvelWebService

class SeriesRepositoryImpl(private val webService: MarvelWebService) : SeriesRepository {

    private val responseHandler = ResponseHandler()

    override suspend fun series(characterId: Int): ResourceModel<SeriesResponse> {
        return try {
            responseHandler.handleSuccess(webService.series(characterId).remoteMarvelData as SeriesResponse)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}