package com.marvelcomics.brito.data.repository.comics

import com.marvelcomics.brito.data.datasource.remote.response.ComicResponse
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.data.handler.ResponseHandler
import com.marvelcomics.brito.data.webservice.MarvelWebService

class ComicRepositoryImpl(private val webService: MarvelWebService) : ComicRepository {

    private val responseHandler = ResponseHandler()

    override suspend fun comics(characterId: Int): ResourceModel<ComicResponse> {
        return try {
            responseHandler.handleSuccess(webService.comics(characterId).remoteMarvelData as ComicResponse)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}