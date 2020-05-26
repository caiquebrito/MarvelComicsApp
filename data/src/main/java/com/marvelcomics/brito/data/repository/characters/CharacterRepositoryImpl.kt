package com.marvelcomics.brito.data.repository.characters

import com.marvelcomics.brito.data.datasource.remote.mapper.CharacterMapper
import com.marvelcomics.brito.data.entity.CharacterEntity
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.data.handler.ResponseHandler
import com.marvelcomics.brito.data.webservice.MarvelWebService
import com.marvelcomics.brito.infrastructure.exception.MarvelMapperException

class CharacterRepositoryImpl(
    private val webService: MarvelWebService,
    private val characterMapper: CharacterMapper
) : CharacterRepository {

    private val responseHandler = ResponseHandler()

    override suspend fun getCharacters(name: String): ResourceModel<CharacterEntity> {
        return try {
            val listEntity = characterMapper.transform(webService.characters(name))
            listEntity?.let {
                responseHandler.handleSuccess(it[0])
            } ?: let {
                throw MarvelMapperException("Error mapping character", null)
            }
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}