package com.marvelcomics.brito.data.repository.characters

import com.marvelcomics.brito.data.datasource.remote.response.CharacterResponse
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelData
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.data.handler.ResponseHandler
import com.marvelcomics.brito.data.webservice.MarvelWebService

class CharacterRepositoryImpl(private val webService: MarvelWebService) : CharacterRepository {

    private val responseHandler = ResponseHandler()

    override suspend fun characters(name: String): ResourceModel<RemoteMarvelData<CharacterResponse>> {
        return try {
            responseHandler.handleSuccess(webService.characters(name).remoteMarvelData as RemoteMarvelData<CharacterResponse>)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}